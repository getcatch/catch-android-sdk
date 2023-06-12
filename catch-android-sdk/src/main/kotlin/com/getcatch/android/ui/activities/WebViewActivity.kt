package com.getcatch.android.ui.activities

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.getcatch.android.BuildConfig
import com.getcatch.android.exceptions.WebViewError
import com.getcatch.android.ui.theming.color.CatchColors
import com.getcatch.android.web.CatchSDKWebClient
import com.getcatch.android.web.CatchWebViewInterface
import com.getcatch.android.web.PostMessageBody
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber

internal abstract class WebViewActivity : ComponentActivity() {
    protected var webView: WebView? = null
    private val webViewClient = CatchSDKWebClient(this)
    private val javascriptInterface = CatchWebViewInterface(this)
    protected val url: MutableState<String?> = mutableStateOf(null)

    /**
     * Each WebView activity needs to implement this function to set the value
     * of [url]. It is expected that observable sources will be used in url
     * generation, so each subclass is responsible for making sure the value
     * ends up in the [url] state.
     */
    protected abstract fun generateUrl()

    abstract fun handlePostMessage(message: PostMessageBody)

    open fun handleError(error: WebViewError?) {
        Timber.e(error)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generateUrl()
        setContent {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val currentUrl = url.value
                if (currentUrl == null) {
                    CircularProgressIndicator(color = Color(CatchColors.PINK_2))
                    return@setContent
                }

                val webViewState = rememberWebViewState(currentUrl)
                if (webViewState.isLoading) {
                    CircularProgressIndicator(color = Color(CatchColors.PINK_2))
                }
                WebView(
                    modifier = Modifier.fillMaxSize(),
                    state = webViewState,
                    client = webViewClient,
                    onCreated = { view ->
                        // Set transparent background
                        view.setBackgroundColor(0)

                        // Enable core browser functionality
                        view.settings.javaScriptEnabled = true
                        view.settings.domStorageEnabled = true
                        view.settings.databaseEnabled = true
                        view.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

                        // Inject javascript interface
                        view.addJavascriptInterface(
                            javascriptInterface,
                            CatchWebViewInterface.NAME
                        )

                        if (BuildConfig.DEBUG) {
                            WebView.setWebContentsDebuggingEnabled(true)
                        }

                        webView = view
                    },
                    onDispose = { view ->
                        webViewClient.onWebViewDisposed(view)
                        webView = null
                    },
                    captureBackPresses = false,
                )
            }
        }
    }

    protected fun postMessage(message: PostMessageBody) {
        val jsonString = Json.encodeToString(message)
        webView?.evaluateJavascript(
            "window.postMessage($jsonString)"
        ) { }
    }
}
