package com.getcatch.android.activities.tofu

import android.util.Log
import com.getcatch.android.activities.WebViewActivity
import com.getcatch.android.web.CatchWebViewInterface
import com.getcatch.android.web.PostMessageBody

internal class TOFUWebViewInterface(activity: WebViewActivity) : CatchWebViewInterface(activity) {
    override fun handlePostMessage(message: PostMessageBody) {
        when (message.action) {
            ACTION_BACK -> activity.finish()
            else -> Log.d(this::class.simpleName, "Unhandled post message: $message")
        }
    }

    companion object {
        private const val ACTION_BACK = "CATCH_TOFU_BACK"
    }
}
