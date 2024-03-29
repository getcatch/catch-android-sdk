package com.getcatch.android.web

import android.webkit.JavascriptInterface
import com.getcatch.android.ui.activities.WebViewActivity
import kotlinx.serialization.SerializationException
import timber.log.Timber

internal class CatchWebViewInterface(val webViewActivity: WebViewActivity) {

    @JavascriptInterface
    fun handlePostMessage(message: String) {
        if (message.isBlank() || message == EMPTY_JSON_STRING) {
            return
        }
        try {
            val deserializedMessage = PostMessageBody.fromJsonString(message)
            webViewActivity.handlePostMessage(deserializedMessage)
        } catch (ex: SerializationException) {
            Timber.w(
                t = ex,
                message = "Error deserializing a post message body",
            )
        }
    }

    companion object {
        private const val EMPTY_JSON_STRING = "\"\""
        const val NAME = "CatchAndroid"
    }
}
