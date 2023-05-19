package com.getcatch.android.ui.activities.checkout.virtualcard

import androidx.lifecycle.lifecycleScope
import com.getcatch.android.models.PublicKey
import com.getcatch.android.models.checkout.CreateVirtualCardCheckoutData
import com.getcatch.android.models.checkout.VirtualCardCheckoutSuccessData
import com.getcatch.android.network.Environment
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.serialization.SnakeCaseSerializer
import com.getcatch.android.ui.activities.checkout.CheckoutActivity
import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.web.PostMessageActions
import com.getcatch.android.web.PostMessageBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class VirtualCardCheckoutActivity
    : CheckoutActivity<VirtualCardCheckoutResult>(), KoinComponent {
    private val environment: Environment by inject()
    private val publicKey: PublicKey by inject()
    private val merchantRepo: MerchantRepository by inject()
    private val checkoutReady = MutableStateFlow(false)
    private val checkoutData = MutableStateFlow<CreateVirtualCardCheckoutData?>(null)

    override fun generateUrl() {
        val args = VirtualCardCheckoutContract.Args.fromIntent(intent)
        if (args == null) {
            finishWithError(defaultInitializationError)
            return
        }
        lifecycleScope.launch {
            merchantRepo.activeMerchant.collect { merchant ->
                if (merchant != null) {
                    if (args.checkoutId != null) {
                        url.value = CatchUrls.openVirtualCardCheckout(
                            environment = environment,
                            publicKey = publicKey,
                            merchant = merchant,
                            checkoutId = args.checkoutId,
                            prefill = args.prefill,
                        )
                    } else if (args.checkoutData != null) {
                        url.value = CatchUrls.createAndOpenVirtualCardCheckout(
                            environment = environment,
                            publicKey = publicKey,
                            merchant = merchant,
                            prefill = args.prefill,
                        )
                        checkoutData.value = args.checkoutData
                    } else {
                        finishWithError(defaultInitializationError)
                    }
                }
            }
        }

        lifecycleScope.launch {
            combine(checkoutReady, checkoutData) { ready, data -> Pair(ready, data) }
                .collect { (ready, data) ->
                    if (ready && data != null) {
                        val checkoutDataJson = SnakeCaseSerializer.encodeToJsonElement(data) as JsonObject
                        val messageBody = PostMessageBody(
                            PostMessageActions.VIRTUAL_CARD_CHECKOUT_DATA,
                            data = checkoutDataJson
                        )
                        postMessage(messageBody)
                    }
                }
        }
    }

    override val canceledResult: VirtualCardCheckoutResult = VirtualCardCheckoutResult.Canceled

    override fun generateSuccessResult(successMessage: PostMessageBody): VirtualCardCheckoutResult {
        val successData: VirtualCardCheckoutSuccessData =
            SnakeCaseSerializer.decodeFromJsonElement(successMessage.data!!)
        return VirtualCardCheckoutResult.Confirmed(successData.cardDetails)
    }

    override fun generateFailureResult(error: Throwable) = VirtualCardCheckoutResult.Failed(error)

    override fun generateBundleForResult(result: VirtualCardCheckoutResult) =
        VirtualCardCheckoutContract.Result(result).toBundle()

    override fun handleCheckoutReady() {
        checkoutReady.value = true
    }
}
