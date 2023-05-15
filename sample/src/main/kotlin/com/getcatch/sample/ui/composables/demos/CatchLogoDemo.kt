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
import com.getcatch.android.ui.CatchLogoSize
import com.getcatch.android.ui.composables.CatchLogo
import com.getcatch.sample.ui.composables.DemoSection
import com.getcatch.sample.ui.composables.SegmentedControl
import com.getcatch.sample.utils.capitalize

@Composable
fun CatchLogoDemo() {
    var logoSize: CatchLogoSize by remember { mutableStateOf(CatchLogoSize.SMALL) }
    DemoSection(title = "Catch Logo", widgetContent = {
        CatchLogo(size = logoSize)
    }, settingsContent = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Size")
            SegmentedControl(
                options = CatchLogoSize.values().toList().map { it.name.lowercase().capitalize() },
                onOptionSelected = { logoSize = CatchLogoSize.values()[it] }
            )
        }
    })
}
