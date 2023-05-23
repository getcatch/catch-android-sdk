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
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.getcatch.sample.DemoSettingsViewModel
import com.getcatch.sample.ui.theming.DarkDemoColors
import com.getcatch.sample.ui.theming.LightDemoColors
import com.getcatch.sample.utils.allCatchColorThemes
import com.getcatch.sample.utils.centsToDollarsString
import com.getcatch.sample.utils.name
import com.getcatch.sample.utils.roundCentsToDollar
import kotlin.math.roundToInt

const val FIVE_HUNDRED_DOLLARS_CENTS = 50000f

@Composable
fun DemoSettingsSheet(viewModel: DemoSettingsViewModel) {
    MaterialTheme(colors = if (viewModel.colorTheme.isDarkTheme) DarkDemoColors else LightDemoColors) {
        var sliderPosition by remember { mutableStateOf(0f) }
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

            DemoSettingSheetSection(headerText = "Price: ${centsToDollarsString(sliderPosition.roundToInt())}") {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        sliderPosition = 0f
                        viewModel.clearPrice()
                    }, enabled = viewModel.price != 0) {
                        Text(text = "Clear")
                    }
                    Spacer(Modifier.width(8.dp))
                    Slider(
                        modifier = Modifier.weight(1f),
                        value = sliderPosition,
                        onValueChange = {
                            sliderPosition = it
                        },
                        onValueChangeFinished = {
                            viewModel.updatePrice(roundCentsToDollar(sliderPosition))
                        },
                        valueRange = 0f..FIVE_HUNDRED_DOLLARS_CENTS,
                        steps = 500,
                        colors = SliderDefaults.colors(
                            inactiveTrackColor = MaterialTheme.colors.primary,
                            activeTrackColor = MaterialTheme.colors.background,
                            thumbColor = MaterialTheme.colors.primary,
                        )
                    )
                }
            }

            DemoSettingSheetSection("Color Theme") {
                SegmentedControl(
                    options = allCatchColorThemes.map { it.name },
                    onOptionSelected = { viewModel.updateCatchColorTheme(allCatchColorThemes[it]) }
                )
            }
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
