package com.getcatch.android.activities

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.getcatch.android.CatchUrls
import com.getcatch.android.repository.MerchantRepository
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class TOFUActivity : ComponentActivity(), KoinComponent {
    private var webView: WebView? = null
    private val merchantRepo: MerchantRepository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val activeMerchant by merchantRepo.activeMerchant.collectAsState()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (activeMerchant != null) {
                    val url = CatchUrls.eduModal(merchant = activeMerchant!!)
                    val webViewState = rememberWebViewState(url = url)
                    WebView(
                        state = webViewState,
                        client = remember { CatchSDKWebClient() },
                        onCreated = {
                            it.settings.javaScriptEnabled = true
                            it.setBackgroundColor(0)
                            it.settings.domStorageEnabled = true
                            it.settings.databaseEnabled = true
                            it.addJavascriptInterface(
                                TOFUWebViewInterface(this@TOFUActivity),
                                "CatchAndroid"
                            )
                            WebView.setWebContentsDebuggingEnabled(true)
                            webView = it
                        },
                        onDispose = {
                            removeListener(it)
                            webView = null
                        },
                        captureBackPresses = false,
                        modifier = Modifier.weight(fill = true, weight = 1f),

                        )
                    Button(onClick = this@TOFUActivity::postMessage) {
                        Text("SEND MESSAGE")
                    }
                } else {
                    Text(text = "Merchant not yet loaded")
                }
            }
        }
    }

    fun removeListener(webView: WebView) {
        val script =
            "window.removeEventListener('message', (event) => CatchAndroid.handlePostMessage(event.data))"
        webView.evaluateJavascript(script) {
            Log.d("TOFUActivity", "Removing listener")
        }
    }

    fun postMessage() {
        webView?.evaluateJavascript(
            "window.postMessage(\"{action: 'CATCH_TOFU_OPEN'}\", '*')"
        ) {
            Log.d("TOFUActivity", it)
        }
    }
}

@Suppress("MaxLineLength")
internal class CatchSDKWebClient : AccompanistWebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        val registerLoggerScript =
            "window.addEventListener('message', (event) => console.log({androidLogged: event}))"
        view?.evaluateJavascript(registerLoggerScript) {
            Log.d("TOFUActivity", "Registering listener message handler")
        }

        val registerEventHandlerScript =
            "window.addEventListener('message', (event) => { CatchAndroid.handlePostMessage(JSON.stringify(event.data))})"
        view?.evaluateJavascript(registerEventHandlerScript) {
            Log.d("TOFUActivity", "Registering listener message handler")
        }
    }
}
