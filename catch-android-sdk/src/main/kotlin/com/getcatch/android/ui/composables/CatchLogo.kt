package com.getcatch.android.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.getcatch.android.R
import com.getcatch.android.ui.CatchLogoSize
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.utils.logoResId
import com.getcatch.android.utils.size

@Composable
public fun CatchLogo(size: CatchLogoSize = CatchLogoSize.SMALL) {
    CatchTheme {
        Image(
            painterResource(id = CatchTheme.variant.logoResId),
            contentDescription = stringResource(id = R.string.content_description_catch_logo),
            modifier = Modifier.size(size),
            contentScale = if (size == CatchLogoSize.FILL) ContentScale.FillWidth else ContentScale.Fit
        )
    }
}
