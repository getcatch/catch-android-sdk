package com.getcatch.android.ui.activities.checkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import com.getcatch.android.exceptions.WebViewError
import com.getcatch.android.models.CatchDeviceTokenPayload
import com.getcatch.android.repository.UserRepository
import com.getcatch.android.ui.activities.WebViewActivity
import com.getcatch.android.web.PostMessageActions
import com.getcatch.android.web.PostMessageBody
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

internal abstract class CheckoutActivity<T> : WebViewActivity(), KoinComponent {
    private val userRepo: UserRepository by inject()

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
                    Timber.e(t = ex, message = "Error setting success result for checkout.")
                }
                finish()
            }

            PostMessageActions.CHECKOUT_BACK -> {
                handleBackPressed()
            }

            PostMessageActions.CHECKOUT_READY -> {
                handleCheckoutReady()
            }

            PostMessageActions.CATCH_DEVICE_TOKEN -> {
                try {
                    val deviceTokenPayload: CatchDeviceTokenPayload = Json.decodeFromJsonElement(message.data!!)
                    deviceTokenPayload.deviceToken?.let { userRepo.updateDeviceToken(it) }
                } catch (ex: Exception) {
                    Timber.e(t = ex, message = "Error updating device token")
                }
            }

            else -> Timber.d("Unhandled post message: ${message.action}")
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
