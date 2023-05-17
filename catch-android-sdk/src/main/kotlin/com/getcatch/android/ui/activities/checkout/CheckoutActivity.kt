package com.getcatch.android.ui.activities.checkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import com.getcatch.android.exceptions.WebViewError
import com.getcatch.android.ui.activities.WebViewActivity
import com.getcatch.android.web.PostMessageActions
import com.getcatch.android.web.PostMessageBody

internal abstract class CheckoutActivity<T> : WebViewActivity() {

    protected abstract val canceledResult: T

    protected abstract fun generateSuccessResult(successMessage: PostMessageBody): T

    protected abstract fun generateFailureResult(error: Throwable): T

    protected abstract fun generateBundleForResult(result: T): Bundle

    @Suppress("TooGenericExceptionCaught")
    override fun handlePostMessage(message: PostMessageBody) = runOnUiThread {
        when (message.action) {
            PostMessageActions.CHECKOUT_SUCCESS -> {
                try {
                    setActivityResult(generateSuccessResult(message))
                } catch (ex: Exception) {
                    Log.e(this::class.simpleName, "Error setting success result for checkout.", ex)
                }
                finish()
            }

            PostMessageActions.CHECKOUT_BACK -> {
                handleBackPressed()
            }

            PostMessageActions.CHECKOUT_READY -> {
                handleCheckoutReady()
            }

            else -> Log.d(this::class.simpleName, "Unhandled post message: ${message.action}")
        }
    }

    protected open fun handleCheckoutReady() { /* Default no-op */ }

    override fun handleError(
        error: WebViewError?
    ) {
        finishWithError(error = error ?: defaultWebViewError)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback { handleBackPressed() }
    }

    private fun handleBackPressed() {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            setActivityResult(canceledResult)
            finish()
        }
    }

    private fun setActivityResult(result: T) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtras(generateBundleForResult(result))
        )
    }

    protected fun finishWithError(error: Throwable) {
        setActivityResult(generateFailureResult(error))
        finish()
    }

    protected val defaultInitializationError: IllegalArgumentException
        get() = IllegalArgumentException("Checkout started without arguments.")

    private val defaultWebViewError: WebViewError
        get() = WebViewError("User experienced an error during checkout.")
}
