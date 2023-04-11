package com.getcatch.android.ui.composables.elements

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.getcatch.android.ui.styles.InfoWidgetStyle

@Composable
internal fun FillerText(
    modifier: Modifier = Modifier,
    text: String,
    styles: InfoWidgetStyle.Resolved,
) {
    Text(
        text = styles.applyTextTransform(text),
        style = styles.composeTextStyle,
        modifier = modifier,
    )
}
