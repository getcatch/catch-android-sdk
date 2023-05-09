package com.getcatch.sample.ui.composables.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.composables.PurchaseConfirmation
import com.getcatch.sample.ui.composables.DemoSection
import com.getcatch.sample.ui.composables.SegmentedControl
import com.getcatch.sample.ui.theming.demoCheckboxColors
import com.getcatch.sample.utils.actionWidgetBorderStyleDemoOptions
import com.getcatch.sample.utils.calculateEarnedAndDonatedRewards
import com.getcatch.sample.utils.name

@Composable
fun PurchaseConfirmationDemo(price: Int) {
    var borderStyle: BorderStyle by remember { mutableStateOf(BorderStyle.SlightRound) }
    var donation: Boolean by remember { mutableStateOf(false) }
    var earned = (price * REWARDS_RATE).toInt()
    var donatedAmount: Int? = null
    if (donation) {
        val result = calculateEarnedAndDonatedRewards(price, REWARDS_RATE)
        earned = result.earned
        donatedAmount = result.donated

    }

    DemoSection(title = "Purchase Confirmation", widgetContent = {
        PurchaseConfirmation(earned = earned, donation = donatedAmount, borderStyle = borderStyle)
    }, settingsContent = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Border Style")
            SegmentedControl(
                options = actionWidgetBorderStyleDemoOptions.map { it.name },
                onOptionSelected = { borderStyle = actionWidgetBorderStyleDemoOptions[it] }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = donation,
                onCheckedChange = { donation = it },
                colors = demoCheckboxColors,
            )
            Text("Donated credits")
        }
    })
}

const val REWARDS_RATE = 0.1
