package com.getcatch.android.ui.activities.tofu

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.getcatch.android.exceptions.WebViewError
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.repository.UserRepository
import com.getcatch.android.ui.activities.WebViewActivity
import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.web.PostMessageActions
import com.getcatch.android.web.PostMessageBody
import com.getcatch.android.web.PostMessageBodyKeys
import kotlinx.coroutines.launch
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class TOFUActivity : WebViewActivity(), KoinComponent {

    private val environment: Environment by inject()
    private val publicKey: PublicKey by inject()
    private val merchantRepo: MerchantRepository by inject()
    private val userRepo: UserRepository by inject()

    override fun generateUrl() {
        lifecycleScope.launch {
            merchantRepo.activeMerchant.collect { merchant ->
                if (merchant != null) {
                    url.value = CatchUrls.eduModal(environment, publicKey, merchant)
                }
            }
        }
    }

    override fun handlePostMessage(message: PostMessageBody) = runOnUiThread {
        when (message.action) {
            PostMessageActions.TOFU_BACK -> {
                finish()
            }

            PostMessageActions.TOFU_DEVICE_TOKEN -> {
                val deviceToken: String? =
                    message.data?.get(PostMessageBodyKeys.DEVICE_TOKEN)?.jsonPrimitive?.contentOrNull
                if (deviceToken != null) {
                    userRepo.updateDeviceToken(deviceToken)
                }
            }

            else -> Log.d(this::class.simpleName, "Unhandled post message: $message")
        }
    }

    override fun handleError(
        error: WebViewError?
    ) {
        // Do nothing as the user can easily close TOFU if they are
        // in a broken state and we aren't certain that the error is
        // a sure indicator of a broken state.
    }
}
