package com.getcatch.android.composables.elements

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
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.utils.Constants

@Composable
internal fun InlineLogo(fontSize: TextUnit) {
    val logoHeight = fontSize.value * Constants.LOGO_TO_FILLER_TEXT_HEIGHT_RATIO
    Image(
        painter = painterResource(id = CatchTheme.variant.logoResId),
        contentDescription = stringResource(
            id = R.string.content_description_catch_logo
        ),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .height(height = logoHeight.dp)
            .aspectRatio(
                Constants.LOGO_ASPECT_RATIO,
                matchHeightConstraintsFirst = true
            ),
    )
}
