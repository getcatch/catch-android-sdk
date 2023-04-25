package com.getcatch.android.web

import android.net.Uri
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.getcatch.android.ui.activities.WebViewActivity
import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.utils.baseUrl
import com.getcatch.android.utils.launchUrlIntent
import com.google.accompanist.web.AccompanistWebViewClient

@Suppress("MaxLineLength")
internal class CatchSDKWebClient(private val webViewActivity: WebViewActivity) : AccompanistWebViewClient() {

    private fun registerPostMessageListener(view: WebView?) {
        val createPostMessageHandlerFunctionScript =
            "function $POST_MESSAGE_HANDLER_FUNCTION_NAME(event) { CatchAndroid.handlePostMessage(JSON.stringify(event.data))}"

        view?.evaluateJavascript(createPostMessageHandlerFunctionScript) {
            Log.d(this::class.simpleName, "Creating post message handler function")
        }

        val registerEventHandlerScript =
            "window.addEventListener('message', $POST_MESSAGE_HANDLER_FUNCTION_NAME)"
        view?.evaluateJavascript(registerEventHandlerScript) {
            Log.d(this::class.simpleName, "Registering listener post message handler")
        }
    }

    private fun shouldOpenUrlInExternalBrowser(url: String): Boolean {
        return if (url in CatchUrls.internalWebViewBaseUrls) {
            false
        } else {
            launchUrlIntent(webViewActivity, url)
            true
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return if (request == null || !shouldOpenUrlInExternalBrowser(request.url.baseUrl)) {
            super.shouldOverrideUrlLoading(view, request)
        } else {
            true
        }
    }

    /**
     * We need to override this deprecated method to make sure our webviews function properly on
     * devices running Android before API 24
     */
    @Suppress("deprecation")
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return if (url == null || !shouldOpenUrlInExternalBrowser(Uri.parse(url).baseUrl)) {
            super.shouldOverrideUrlLoading(view, url)
        } else {
            true
        }
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        registerPostMessageListener(view)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        webViewActivity.finish()
    }

    companion object {
        const val POST_MESSAGE_HANDLER_FUNCTION_NAME = "catchAndroidPostMessageHandler"
    }
}
