package com.getcatch.android.ui.activities.checkout

import android.util.Log
import com.getcatch.android.ui.activities.WebViewActivity
import com.getcatch.android.web.CatchWebViewInterface
import com.getcatch.android.web.PostMessageBody

internal class CheckoutWebViewInterface(activity: WebViewActivity) : CatchWebViewInterface(activity) {
    override fun handlePostMessage(message: PostMessageBody) {
        when (message.action) {
            else -> Log.d(this::class.simpleName, "Unhandled post message: $message")
        }
    }
}
