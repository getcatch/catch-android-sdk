package com.getcatch.android.activities

import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.getcatch.android.web.PostMessageBody
import kotlinx.serialization.SerializationException

internal class TOFUWebViewInterface(private val activity: TOFUActivity) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String) {
        Toast.makeText(activity, toast, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun handlePostMessage(message: String) {
        if (message.isBlank() || message == EMPTY_JSON_STRING) {
            return
        }
        try {
            val body = PostMessageBody.fromJsonString(message)
            if (body.action == "CATCH_TOFU_BACK") {
                activity.finish()
            }
        } catch (ex: SerializationException) {
            Log.e(
                TAG,
                "Error deserializing the following into a PostMessageBody: $message",
                ex
            )
        }
    }

    companion object {
        private const val TAG = "TOFUWebViewInterface"
        private const val EMPTY_JSON_STRING = "\"\""
    }
}
