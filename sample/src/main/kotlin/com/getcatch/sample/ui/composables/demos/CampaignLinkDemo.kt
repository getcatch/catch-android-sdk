package com.getcatch.sample.ui.composables.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.composables.CampaignLink
import com.getcatch.sample.ui.composables.DemoSection
import com.getcatch.sample.ui.composables.SegmentedControl
import com.getcatch.sample.utils.actionWidgetBorderStyleDemoOptions
import com.getcatch.sample.utils.name

@Composable
fun CampaignLinkDemo() {
    var borderStyle: BorderStyle by remember { mutableStateOf(BorderStyle.SlightRound) }
    DemoSection(title = "Campaign Link", widgetContent = {
        CampaignLink(campaignName = DEMO_CAMPAIGN_NAME, borderStyle = borderStyle)
    }, settingsContent =  {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Border Style")
            SegmentedControl(
                options = actionWidgetBorderStyleDemoOptions.map { it.name },
                onOptionSelected = { borderStyle = actionWidgetBorderStyleDemoOptions[it] }
            )
        }
    })
}

const val DEMO_CAMPAIGN_NAME = "RAWYP"
