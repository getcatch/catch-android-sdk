package com.getcatch.sample.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.getcatch.sample.DemoSettingsViewModel
import com.getcatch.sample.utils.allThemeVariants
import com.getcatch.sample.utils.centsToDollarsString
import com.getcatch.sample.utils.name

@Composable
fun DemoSettingsSheet(viewModel: DemoSettingsViewModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {

        Box(
            Modifier
                .width(80.dp)
                .height(6.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(percent = 50)
                )
        )

        DemoSettingSheetSection(headerText = "Price: ${centsToDollarsString(viewModel.price)}") {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { viewModel.subtractTenDollars() }, enabled = viewModel.price != 0) {
                    Text(text = "-$10")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { viewModel.subtractOneDollar() }, enabled = viewModel.price != 0) {
                    Text(text = "-$1")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { viewModel.addOneDollar() }) {
                    Text(text = "+$1")
                }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { viewModel.addTenDollars() }) {
                    Text(text = "+$10")
                }
            }
        }

        DemoSettingSheetSection("Theme Variant") {
            SegmentedControl(
                options = allThemeVariants.map { it.name },
                onOptionSelected = { viewModel.updateThemeVariant(allThemeVariants[it]) }
            )
        }
    }
}

@Composable
fun DemoSettingSheetSection(headerText: String, content: @Composable () -> Unit) {
    Text(headerText, style = MaterialTheme.typography.h6)
    Spacer(Modifier.height(4.dp))
    content()
    Spacer(Modifier.height(16.dp))
}
