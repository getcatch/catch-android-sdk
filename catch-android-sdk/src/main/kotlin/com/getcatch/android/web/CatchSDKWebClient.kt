package com.getcatch.android.web

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.getcatch.android.exceptions.WebViewError
import com.getcatch.android.network.Environment
import com.getcatch.android.ui.activities.WebViewActivity
import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.utils.baseUrl
import com.getcatch.android.utils.launchUrlIntent
import com.google.accompanist.web.AccompanistWebViewClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Suppress("MaxLineLength")
internal class CatchSDKWebClient(private val webViewActivity: WebViewActivity) :
    AccompanistWebViewClient(), KoinComponent {
    private val environment: Environment by inject()

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (view != null) {
            registerPostMessageListener(view)
        }
    }

    fun onWebViewDisposed(view: WebView) {
        removePostMessageListener(view)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            error?.description?.toString()?.let {
                webViewActivity.handleError(WebViewError(it))
            }
            return
        }
        webViewActivity.handleError(null)
    }

    private fun registerPostMessageListener(view: WebView) {
        val createPostMessageHandlerFunctionScript = """
            function $POST_MESSAGE_HANDLER_FUNCTION_NAME(event) {
                if (event.origin != "${environment.baseUrl}") return;
                CatchAndroid.handlePostMessage(JSON.stringify(event.data));
            }
        """.trimIndent()

        view.evaluateJavascript(createPostMessageHandlerFunctionScript) {}

        val registerEventHandlerScript =
            "window.addEventListener('message', $POST_MESSAGE_HANDLER_FUNCTION_NAME)"
        view.evaluateJavascript(registerEventHandlerScript) {}
    }

    @Suppress("MaxLineLength")
    private fun removePostMessageListener(webView: WebView) {
        val script =
            "window.removeEventListener('message', $POST_MESSAGE_HANDLER_FUNCTION_NAME)"
        webView.evaluateJavascript(script) {}
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

    companion object {
        const val POST_MESSAGE_HANDLER_FUNCTION_NAME = "catchAndroidPostMessageHandler"
    }
}
