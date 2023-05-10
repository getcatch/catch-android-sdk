package com.getcatch.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.getcatch.android.ui.views.CalloutView
import com.getcatch.android.ui.views.CampaignLinkView
import com.getcatch.android.ui.views.ExpressCheckoutCalloutView
import com.getcatch.android.ui.views.PaymentMethodView
import com.getcatch.android.ui.views.PurchaseConfirmationView
import com.getcatch.sample.databinding.ActivityViewBasedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ViewBasedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBasedBinding
    private lateinit var basicCallout: CalloutView
    private lateinit var customCallout: CalloutView
    private lateinit var basicPaymentMethod: PaymentMethodView
    private lateinit var customPaymentMethod: PaymentMethodView
    private lateinit var basicExpressCheckoutCallout: ExpressCheckoutCalloutView
    private lateinit var customExpressCheckoutCallout: ExpressCheckoutCalloutView
    private lateinit var basicPurchaseConfirmation: PurchaseConfirmationView
    private lateinit var customPurchaseConfirmation: PurchaseConfirmationView
    private lateinit var basicCampaignLink: CampaignLinkView
    private lateinit var customCampaignLink: CampaignLinkView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_based)
        basicCallout = binding.basicCallout
        basicCallout.setOnClickListener {
            if (basicCallout.price == null) {
                basicCallout.price = Random.nextInt(from = 0, until = 10000)
            } else {
                basicCallout.price = null
            }
        }
        customCallout = binding.customCallout
        customCallout.setOnClickListener  {
            if (customCallout.price == null) {
                customCallout.price = Random.nextInt(from = 0, until = 10000)
            } else {
                customCallout.price = null
            }
        }
        basicPaymentMethod = binding.basicPaymentMethod
        customPaymentMethod = binding.customPaymentMethod
        basicExpressCheckoutCallout = binding.basicExpressCheckoutCallout
        customExpressCheckoutCallout = binding.customExpressCheckoutCallout
        basicPurchaseConfirmation = binding.basicPurchaseConfirmation
        customPurchaseConfirmation = binding.customPurchaseConfirmation
        basicCampaignLink = binding.basicCampaignLink
        customCampaignLink = binding.customCampaignLink
        basicCampaignLink.campaignName = "RAWYP"
        customCampaignLink.campaignName = "RAWYP"
    }
}
