package com.getcatch.android.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.getcatch.android.Catch
import com.getcatch.android.R
import com.getcatch.android.ui.CatchLogoSize
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.utils.logoResId
import com.getcatch.android.utils.size

/**
 * A view which displays Catch's logo.
 *
 * @param size The [`CatchLogoSize`](CatchLogoSize) options are meant to provide some recommended
 * defaults while allowing for customization by using the [`CatchLogoSize.FILL`](CatchLogoSize.FILL)
 * within a container of the size of your choosing.
 *
 * @param colorTheme The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set
 * globally on the [`Catch`](Catch) object will be used, which defaults to
 * [`CatchColorTheme.Light`](CatchColorTheme.Light).
 */
@Composable
public fun CatchLogo(size: CatchLogoSize = CatchLogoSize.SMALL, colorTheme: CatchColorTheme? = null) {
    Catch.assertInitialized()
    CatchTheme(colorTheme) {
        Image(
            painterResource(id = CatchTheme.colorTheme.logoResId),
            contentDescription = stringResource(id = R.string.content_description_catch_logo),
            modifier = Modifier.size(size),
            contentScale = if (size == CatchLogoSize.FILL) ContentScale.FillWidth else ContentScale.Fit
        )
    }
}
