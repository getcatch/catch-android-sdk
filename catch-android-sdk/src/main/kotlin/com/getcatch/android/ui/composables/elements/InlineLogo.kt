package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.ui.InfoWidgetType
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.utils.Constants
import com.getcatch.android.utils.logoResId

@Composable
internal fun InlineLogo(fontSize: TextUnit, widgetType: InfoWidgetType, modifier: Modifier = Modifier) {
    val logoHeight = fontSize.value * widgetType.logoToFontSizeRatio
    Image(
        painter = painterResource(id = CatchTheme.variant.logoResId),
        contentDescription = stringResource(
            id = R.string.content_description_catch_logo
        ),
        contentScale = ContentScale.Fit,
        modifier = modifier
            .height(height = logoHeight.dp)
            .aspectRatio(
                Constants.LOGO_ASPECT_RATIO,
                matchHeightConstraintsFirst = true
            ),
    )
}
