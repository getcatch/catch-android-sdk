package com.getcatch.android.web

import android.util.Log
import android.webkit.JavascriptInterface
import com.getcatch.android.activities.WebViewActivity
import kotlinx.serialization.SerializationException

internal abstract class CatchWebViewInterface(protected val activity: WebViewActivity) {

    @JavascriptInterface
    fun handlePostMessage(message: String) {
        if (message.isBlank() || message == EMPTY_JSON_STRING) {
            return
        }
        try {
            val deserializedMessage = PostMessageBody.fromJsonString(message)
            handlePostMessage(deserializedMessage)
        } catch (ex: SerializationException) {
            Log.e(
                TAG,
                "Error deserializing the following into a PostMessageBody: $message",
                ex
            )
        }
    }

    protected abstract fun handlePostMessage(message: PostMessageBody)

    companion object {
        private const val TAG = "WebViewInterface"
        private const val EMPTY_JSON_STRING = "\"\""
    }
}
