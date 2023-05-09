package com.getcatch.android.ui.composables.elements

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariant

@Composable
internal fun LoadingBar(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val percentage by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(durationMillis = 500))
    )
    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(CatchTheme.colors.border, shape = RoundedCornerShape(2.dp))
                .fillMaxHeight()
                .fillMaxWidth(percentage)
        )
    }
}

@Preview
@Composable
private fun PreviewLoadingBar() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        CatchTheme {
            Box(
                modifier = Modifier
                    .width(115.dp)
                    .height(50.dp)
            ) {
                LoadingBar()
            }
        }

        CatchTheme(variant = ThemeVariant.Dark) {
            Box(
                modifier = Modifier
                    .width(115.dp)
                    .height(50.dp)
            ) {
                LoadingBar()
            }
        }
    }
}
