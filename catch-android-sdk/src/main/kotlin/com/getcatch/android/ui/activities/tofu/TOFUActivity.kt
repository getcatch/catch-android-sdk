package com.getcatch.android.ui.activities.tofu

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.models.PublicKey
import com.getcatch.android.models.tofu.MerchantDefaults
import com.getcatch.android.models.tofu.TofuOpenData
import com.getcatch.android.models.tofu.TofuPath
import com.getcatch.android.network.Environment
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.repository.UserRepository
import com.getcatch.android.ui.activities.WebViewActivity
import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.utils.parcelableExtra
import com.getcatch.android.web.PostMessageActions
import com.getcatch.android.web.PostMessageBody
import com.getcatch.android.web.PostMessageBodyKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

internal class TOFUActivity : WebViewActivity(), KoinComponent {

    private val environment: Environment by inject()
    private val publicKey: PublicKey by inject()
    private val merchantRepo: MerchantRepository by inject()
    private val userRepo: UserRepository by inject()
    private val tofuReady = MutableStateFlow(false)
    private val tofuOpenData = MutableStateFlow<TofuOpenData?>(null)

    override fun generateUrl() {
        val args: TofuArgs? = intent.parcelableExtra(EXTRA_ARGS)
        lifecycleScope.launch {
            combine(merchantRepo.activeMerchant, userRepo.activeUser) { merchant, user ->
                Pair(
                    merchant,
                    user
                )
            }
                .collect { (merchant, user) ->
                    if (merchant != null) {
                        url.value = CatchUrls.tofu(
                            environment = environment,
                            publicKey = publicKey,
                            merchant = merchant,
                            path = args?.path ?: TofuPath.HOW_IT_WORKS
                        )
                        if (args != null && user != null) {
                            tofuOpenData.value = TofuOpenData(
                                earnedRewardsBreakdown = args.rewardsSummary,
                                price = args.price,
                                merchantDefaults = MerchantDefaults.fromMerchant(merchant),
                                donationRecipient = merchant.donationRecipient,
                                publicUserData = user,
                                path = args.path,
                            )
                        }
                    }
                }
        }
        lifecycleScope.launch {
            combine(tofuReady, tofuOpenData) { ready, data -> Pair(ready, data) }
                .collect { (ready, data) ->
                    if (ready && data != null) {
                        val messageBody =
                            PostMessageBody(
                                PostMessageActions.TOFU_OPEN,
                                data = data.toJsonObject()
                            )
                        postMessage(messageBody)
                    }
                }
        }
    }

    override fun handlePostMessage(message: PostMessageBody) = runOnUiThread {
        when (message.action) {
            PostMessageActions.TOFU_LISTENING -> {
                postMessage(PostMessageBody(PostMessageActions.TOFU_LOAD))
            }

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

            PostMessageActions.TOFU_READY -> {
                tofuReady.value = true
            }

            else -> Timber.d("Unhandled post message: $message")
        }
    }

    companion object {
        const val EXTRA_ARGS =
            "com.getcatch.android.ui.activities.tofu.TOFUActivity.extra_args"

        private fun createIntent(
            context: Context,
            tofuArgs: TofuArgs?,
        ): Intent {
            return Intent(context, TOFUActivity::class.java).putExtra(EXTRA_ARGS, tofuArgs)
        }

        fun open(
            context: Context,
            price: Int?,
            rewardsSummary: EarnedRewardsSummary?,
            path: TofuPath = TofuPath.HOW_IT_WORKS
        ) {
            val args =
                if (price == null || rewardsSummary == null) null
                else TofuArgs(
                    price = price,
                    rewardsSummary = rewardsSummary,
                    path = path,
                )

            val intent = createIntent(context, args)
            context.startActivity(intent)
        }
    }
}
