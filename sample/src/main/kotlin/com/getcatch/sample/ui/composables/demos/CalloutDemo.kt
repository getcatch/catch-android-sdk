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
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.composables.Callout
import com.getcatch.sample.ui.composables.DemoSection
import com.getcatch.sample.ui.composables.SegmentedControl
import com.getcatch.sample.ui.theming.demoCheckboxColors
import com.getcatch.sample.utils.calloutBorderStyleDemoOptions
import com.getcatch.sample.utils.name

@Composable
fun CalloutDemo(price: Int) {
    var borderStyle: CalloutBorderStyle by remember { mutableStateOf(CalloutBorderStyle.None) }
    var hasOrPrefix by remember { mutableStateOf(false) }
    DemoSection(title = "Callout", widgetContent = {
        Callout(price = price, borderStyle = borderStyle, hasOrPrefix = hasOrPrefix)
    }, settingsContent = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Border Style")
            SegmentedControl(
                options = calloutBorderStyleDemoOptions.map { it.name },
                onOptionSelected = { borderStyle = calloutBorderStyleDemoOptions[it] }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = hasOrPrefix,
                onCheckedChange = { hasOrPrefix = it },
                colors = demoCheckboxColors
            )
            Text("Or prefix")
        }
    })
}
