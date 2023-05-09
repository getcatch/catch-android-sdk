package com.getcatch.sample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.android.ui.activities.checkout.direct.DirectCheckoutController
import com.getcatch.sample.ui.composables.DemoScaffold
import com.getcatch.sample.ui.composables.demos.CalloutDemo
import com.getcatch.sample.ui.composables.demos.CampaignLinkDemo
import com.getcatch.sample.ui.composables.demos.CatchLogoDemo
import com.getcatch.sample.ui.composables.demos.ExpressCheckoutCalloutDemo
import com.getcatch.sample.ui.composables.demos.OpenCheckoutDemo
import com.getcatch.sample.ui.composables.demos.PaymentMethodDemo
import com.getcatch.sample.ui.composables.demos.PurchaseConfirmationDemo
import com.getcatch.sample.ui.theming.DarkDemoColors
import com.getcatch.sample.ui.theming.LightDemoColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    lateinit var directCheckoutController: DirectCheckoutController

    private fun openDirectCheckout(checkoutId: String, checkoutPrefill: CheckoutPrefill) {
        directCheckoutController.openCheckout(
            checkoutId,
            checkoutPrefill,
        )
    }

    private fun onCheckoutCanceled() {
        Log.d("TestDirectCheckout", "Cancel")
    }

    private fun onDirectCheckoutConfirmed() {
        Log.d("TestDirectCheckout", "Confirm")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        directCheckoutController = DirectCheckoutController(
            this, this::onDirectCheckoutConfirmed, this::onCheckoutCanceled
        )
        setContent {
            val viewModel: DemoSettingsViewModel = viewModel()
            MaterialTheme(colors = if (viewModel.themeVariant.isDarkTheme) DarkDemoColors else LightDemoColors) {
                DemoScaffold(viewModel) {
                    CalloutDemo(price = viewModel.price)
                    ExpressCheckoutCalloutDemo(price = viewModel.price)
                    PaymentMethodDemo(price = viewModel.price)
                    PurchaseConfirmationDemo(price = viewModel.price)
                    CampaignLinkDemo()
                    CatchLogoDemo()
                    OpenCheckoutDemo(openDirectCheckout = this@ComposeActivity::openDirectCheckout)
                }
            }
        }
    }

}
