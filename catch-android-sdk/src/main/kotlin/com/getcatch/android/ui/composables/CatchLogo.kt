package com.getcatch.android.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.ui.theming.CatchTheme

@Composable
public fun CatchLogo(size: CatchLogoSize = CatchLogoSize.SMALL) {
    CatchTheme {
        Image(
            painterResource(id = CatchTheme.variant.logoResId),
            contentDescription = stringResource(id = R.string.content_description_catch_logo),
            modifier = size.sizeModifier,
            contentScale = if (size == CatchLogoSize.FILL) ContentScale.FillWidth else ContentScale.Fit
        )
    }
}

public enum class CatchLogoSize(internal val sizeModifier: Modifier) {
    SMALL(Modifier.height(18.dp)),
    MEDIUM(Modifier.height(40.dp)),
    FILL(Modifier.fillMaxWidth());
}
