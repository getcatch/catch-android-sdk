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
import com.getcatch.android.ui.composables.ExpressCheckoutCallout
import com.getcatch.sample.ui.composables.DemoSection
import com.getcatch.sample.ui.composables.SegmentedControl
import com.getcatch.sample.utils.infoWidgetBorderStyleDemoOptions
import com.getcatch.sample.utils.name

@Composable
fun ExpressCheckoutCalloutDemo(price: Int) {
    var borderStyle: BorderStyle by remember { mutableStateOf(BorderStyle.None) }
    DemoSection(title = "Express Checkout Callout", widgetContent = {
        ExpressCheckoutCallout(price = price, borderStyle = borderStyle)
    }, settingsContent = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Border Style")
            SegmentedControl(
                options = infoWidgetBorderStyleDemoOptions.map { it.name },
                onOptionSelected = { borderStyle = infoWidgetBorderStyleDemoOptions[it] }
            )
        }
    })
}
