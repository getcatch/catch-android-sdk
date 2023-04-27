package com.getcatch.sample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.activities.checkout.direct.DirectCheckoutController
import com.getcatch.android.ui.composables.Callout
import com.getcatch.android.ui.composables.CampaignLink
import com.getcatch.android.ui.composables.CatchLogo
import com.getcatch.android.ui.composables.CatchLogoSize
import com.getcatch.android.ui.composables.ExpressCheckoutCallout
import com.getcatch.android.ui.composables.PaymentMethod
import com.getcatch.android.ui.composables.PurchaseConfirmation
import com.getcatch.android.ui.styles.ActionButtonStyle
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.theming.ThemeVariant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    lateinit var directCheckoutController: DirectCheckoutController
    private fun goToViewBasedActivity() {
        val intent = Intent(this, ViewBasedActivity::class.java)
        startActivity(intent)
    }

    private fun openTestCheckout() {
        val testCheckoutId = ""
        directCheckoutController.openCheckout(
            testCheckoutId,
            CheckoutPrefill(userName = "Tester")
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
            this,
            this::onDirectCheckoutConfirmed,
            this::onCheckoutCanceled
        )
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Spacer(Modifier.height(16.dp))
                Callout(price = 10000)
                PaymentMethod()
                ExpressCheckoutCallout()
                PurchaseConfirmation(
                    earned = 1000,
                    donation = 50,
                    borderStyle = BorderStyle.SlightRound
                )
                PurchaseConfirmation(
                    earned = 1000,
                    donation = 50,
                    styleOverrides = ActionWidgetStyle(
                        actionButtonStyle = ActionButtonStyle(
                            elevation = 4f
                        )
                    ),
                    borderStyle = BorderStyle.None,
                )
                CampaignLink(campaignName = "aBaVga")
                CampaignLink(
                    campaignName = "aBaVga",
                    borderStyle = BorderStyle.None,
                    theme = ThemeVariant.LightMono
                )
                CatchLogo()
                CatchLogo(size = CatchLogoSize.MEDIUM)
                CatchLogo(size = CatchLogoSize.FILL)
                Button(onClick = { openTestCheckout() }) {
                    Text(text = "Open checkout")
                }
                Button(onClick = { goToViewBasedActivity() }) {
                    Text(text = stringResource(id = R.string.go_to_view_based_activity_btn_label))
                }
            }
        }
    }

}
