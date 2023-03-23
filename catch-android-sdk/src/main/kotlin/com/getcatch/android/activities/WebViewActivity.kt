package com.getcatch.android.activities

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.getcatch.android.BuildConfig
import com.getcatch.android.web.CatchSDKWebClient
import com.getcatch.android.web.CatchWebViewInterface
import com.getcatch.android.web.PostMessageBody
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

internal abstract class WebViewActivity : ComponentActivity(), KoinComponent {
    private var webView: WebView? = null

    protected abstract val javascriptInterface: CatchWebViewInterface
    protected val urlFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val urlState by urlFlow.collectAsState()
            urlState?.let { url ->
                val webViewState = rememberWebViewState(url)
                val webViewClient = remember { CatchSDKWebClient(this) }
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
                        // Inject javascript interface
                        view.addJavascriptInterface(
                            javascriptInterface,
                            JAVASCRIPT_INTERFACE_NAME
                        )
                        if (BuildConfig.DEBUG) {
                            WebView.setWebContentsDebuggingEnabled(true)
                        }
                        webView = view
                    },
                    onDispose = { view ->
                        removeListener(view)
                        webView = null
                    },
                    captureBackPresses = false,
                )
            }
        }
    }

    @Suppress("MaxLineLength")
    private fun removeListener(webView: WebView) {
        val script =
            "window.removeEventListener('message', ${CatchSDKWebClient.POST_MESSAGE_HANDLER_FUNCTION_NAME})"
        webView.evaluateJavascript(script) {
            Log.d(this::class.simpleName, "Removing listener")
        }
    }

    protected fun postMessage(message: PostMessageBody) {
        val jsonString = Json.encodeToString(message)
        webView?.evaluateJavascript(
            "window.postMessage($jsonString)"
        ) {
            Log.d(this::class.simpleName, it)
        }
    }

    companion object {
        private const val JAVASCRIPT_INTERFACE_NAME = "CatchAndroid"
    }
}
