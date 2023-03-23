package com.getcatch.android.activities.tofu

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.getcatch.android.activities.WebViewActivity
import com.getcatch.android.models.PublicKey
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.utils.CatchUrls
import kotlinx.coroutines.launch
import org.koin.core.component.inject

internal class TOFUActivity : WebViewActivity() {
    private val publicKey: PublicKey by inject()
    private val merchantRepo: MerchantRepository by inject()
    override val javascriptInterface = TOFUWebViewInterface(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            merchantRepo.activeMerchant.collect { merchant ->
                if (merchant != null) {
                    urlFlow.value = CatchUrls.eduModal(publicKey, merchant)
                }
            }
        }
    }
}
