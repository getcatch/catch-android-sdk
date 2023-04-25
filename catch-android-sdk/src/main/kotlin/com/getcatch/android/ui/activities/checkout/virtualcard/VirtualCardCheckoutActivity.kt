package com.getcatch.android.ui.activities.checkout.virtualcard


import androidx.lifecycle.lifecycleScope
import com.getcatch.android.models.PublicKey
import com.getcatch.android.models.checkout.CardDetails
import com.getcatch.android.network.Environment
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.serialization.SnakeCaseSerializer
import com.getcatch.android.ui.activities.checkout.CheckoutActivity
import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.web.PostMessageBody
import kotlinx.coroutines.launch
import kotlinx.serialization.json.decodeFromJsonElement
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class VirtualCardCheckoutActivity
    : CheckoutActivity<VirtualCardCheckoutResult>(), KoinComponent {
    private val environment: Environment by inject()
    private val publicKey: PublicKey by inject()
    private val merchantRepo: MerchantRepository by inject()

    override fun generateUrl() {
        val args = VirtualCardCheckoutContract.Args.fromIntent(intent)
        if (args == null) {
            finishWithError(defaultInitializationError)
            return
        }
        lifecycleScope.launch {
            merchantRepo.activeMerchant.collect { merchant ->
                if (merchant != null) {
                    url.value = CatchUrls.virtualCardCheckout(
                        environment = environment,
                        publicKey = publicKey,
                        merchant = merchant,
                        orderId = args.orderId,
                        prefill = args.prefill,
                    )
                }
            }
        }
    }

    override val canceledResult: VirtualCardCheckoutResult = VirtualCardCheckoutResult.Canceled

    override fun generateSuccessResult(successMessage: PostMessageBody): VirtualCardCheckoutResult {
        val cardDetails: CardDetails =
            SnakeCaseSerializer.decodeFromJsonElement(successMessage.data!!)
        return VirtualCardCheckoutResult.Confirmed(cardDetails)
    }

    override fun generateFailureResult(error: Throwable) = VirtualCardCheckoutResult.Failed(error)

    override fun generateBundleForResult(result: VirtualCardCheckoutResult) =
        VirtualCardCheckoutContract.Result(result).toBundle()
}
