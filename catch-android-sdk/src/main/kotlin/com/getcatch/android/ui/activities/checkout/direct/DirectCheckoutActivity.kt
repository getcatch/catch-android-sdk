package com.getcatch.android.ui.activities.checkout.direct

import androidx.lifecycle.lifecycleScope
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.ui.activities.checkout.CheckoutActivity
import com.getcatch.android.utils.CatchUrls
import com.getcatch.android.web.PostMessageBody
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class DirectCheckoutActivity : CheckoutActivity<DirectCheckoutResult>(), KoinComponent {
    private val environment: Environment by inject()
    private val publicKey: PublicKey by inject()
    private val merchantRepo: MerchantRepository by inject()

    override fun generateUrl() {
        val args = DirectCheckoutContract.Args.fromIntent(intent)
        if (args == null) {
            finishWithError(defaultInitializationError)
            return
        }
        lifecycleScope.launch {
            merchantRepo.activeMerchant.collect { merchant ->
                if (merchant != null) {
                    url.value = CatchUrls.directCheckout(
                        environment = environment,
                        publicKey = publicKey,
                        merchant = merchant,
                        checkoutId = args.checkoutId,
                        prefill = args.prefill,
                    )
                }
            }
        }
    }

    override val canceledResult: DirectCheckoutResult = DirectCheckoutResult.Canceled

    override fun generateSuccessResult(successMessage: PostMessageBody) =
        DirectCheckoutResult.Confirmed

    override fun generateFailureResult(error: Throwable) = DirectCheckoutResult.Failed(error)

    override fun generateBundleForResult(result: DirectCheckoutResult) =
        DirectCheckoutContract.Result(result).toBundle()
}
