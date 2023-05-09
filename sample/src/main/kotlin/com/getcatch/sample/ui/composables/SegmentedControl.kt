package com.getcatch.sample.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedControl(options: List<String>, onOptionSelected: (Int) -> Unit) {
    var selectedIndex by remember { mutableStateOf(0) }
    val animatedFraction = selectedIndex.toFloat() / (options.size - 1)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.primary, RoundedCornerShape(8.dp))
            .padding(1.dp),

        ) {

        BoxWithConstraints(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .height(32.dp)
        ) {
            // Sliding background rectangle
            val maxWidth = this.maxWidth.value
            val backgroundOffsetX by animateFloatAsState(
                targetValue = animatedFraction
                    * (maxWidth - 2 /* Padding amount */ - maxWidth / options.size)
                    + selectedIndex + 1
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = backgroundOffsetX.dp)
                    .width(this.maxWidth / options.size - 2.dp)
                    .height(30.dp)
                    .background(MaterialTheme.colors.surface, RoundedCornerShape(6.dp))
            )
        }

        Row(horizontalArrangement = Arrangement.Center) {
            options.forEachIndexed { index, option ->
                val isSelected = index == selectedIndex
                val targetColor =
                    if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary
                val animatedColor by animateColorAsState(targetValue = targetColor)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .clickable {
                            selectedIndex = index
                            onOptionSelected(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        color = animatedColor,
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSegmentedController() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        SegmentedControl(options = listOf("dog", "cat", "pup"), onOptionSelected = {
            println("selected: $it")
        })
    }
}
