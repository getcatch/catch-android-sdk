package com.getcatch.android.activities.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.getcatch.android.activities.WebViewActivity
import com.getcatch.android.domain.PublicKey
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.utils.CatchUrls
import kotlinx.coroutines.launch
import org.koin.core.component.inject

internal class CatchCheckoutActivity : WebViewActivity() {
    private val merchantRepo: MerchantRepository by inject()
    private val publicKey: PublicKey by inject()
    override val javascriptInterface = CheckoutWebViewInterface(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val checkoutId = intent.extras?.getString(EXTRA_CHECKOUT_ID, "") ?: ""
        val prefillUserPhone = intent.extras?.getString(EXTRA_PREFILL_USER_PHONE, "") ?: ""
        val prefillUserName = intent.extras?.getString(EXTRA_PREFILL_USER_NAME, "") ?: ""
        val prefillUserEmail = intent.extras?.getString(EXTRA_PREFILL_USER_EMAIL, "") ?: ""
        val hideHeader = intent.extras?.getBoolean(EXTRA_HIDE_HEADER, false) ?: false
        lifecycleScope.launch {
            merchantRepo.activeMerchant.collect { merchant ->
                if (merchant != null) {
                    urlFlow.value = CatchUrls.checkout(
                        publicKey = publicKey,
                        merchant = merchant,
                        checkoutId = checkoutId,
                        prefillUserPhone = prefillUserPhone,
                        prefillUserName = prefillUserName,
                        prefillUserEmail = prefillUserEmail,
                        hideHeader = hideHeader,
                    )
                }
            }
        }
    }

    companion object {
        private const val EXTRA_CHECKOUT_ID = "EXTRA_CHECKOUT_ID"
        private const val EXTRA_PREFILL_USER_PHONE = "EXTRA_PREFILL_USER_PHONE"
        private const val EXTRA_PREFILL_USER_NAME = "EXTRA_PREFILL_USER_NAME"
        private const val EXTRA_PREFILL_USER_EMAIL = "EXTRA_PREFILL_USER_EMAIL"
        private const val EXTRA_HIDE_HEADER = "EXTRA_HIDE_HEADER"
        fun createIntent(
            context: Context,
            checkoutId: String,
            prefillUserPhone: String = "",
            prefillUserName: String = "",
            prefillUserEmail: String = "",
            hideHeader: Boolean = false,
        ) = Intent(context, CatchCheckoutActivity::class.java).apply {
            putExtra(EXTRA_CHECKOUT_ID, checkoutId)
            putExtra(EXTRA_PREFILL_USER_PHONE, prefillUserPhone)
            putExtra(EXTRA_PREFILL_USER_NAME, prefillUserName)
            putExtra(EXTRA_PREFILL_USER_EMAIL, prefillUserEmail)
            putExtra(EXTRA_HIDE_HEADER, hideHeader)
        }
    }
}
