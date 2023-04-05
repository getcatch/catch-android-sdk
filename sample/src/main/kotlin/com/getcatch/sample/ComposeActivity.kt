package com.getcatch.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.composables.Callout
import com.getcatch.android.ui.composables.CampaignLink
import com.getcatch.android.ui.composables.CatchLogo
import com.getcatch.android.ui.composables.CatchLogoSize
import com.getcatch.android.ui.composables.ExpressCheckoutCallout
import com.getcatch.android.ui.composables.PaymentMethod
import com.getcatch.android.ui.composables.PurchaseConfirmation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    private fun goToViewBasedActivity() {
        val intent = Intent(this, ViewBasedActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Callout()
                Callout(price = 5000)
                PaymentMethod()
                ExpressCheckoutCallout(borderStyle = CalloutBorderStyle.SlightRound)
                PurchaseConfirmation(rewardsAmount = 1000, borderStyle = BorderStyle.SlightRound)
                CampaignLink(rewardsAmount = 1500, borderStyle = BorderStyle.Square)
                CatchLogo()
                CatchLogo(size = CatchLogoSize.MEDIUM)
                CatchLogo(size = CatchLogoSize.FILL)
                Button(onClick = { goToViewBasedActivity() }) {
                    Text(text = stringResource(id = R.string.go_to_view_based_activity_btn_label))
                }
            }
        }
    }
}
