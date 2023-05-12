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
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.logoResId
import com.getcatch.android.utils.size

/**
 * A view which displays Catch's logo.
 *
 * @param size The [CatchLogoSize] options are meant to provide some recommended defaults while
 * allowing for customization by using the [CatchLogoSize.FILL] within a container of the size of
 * your choosing.
 *
 * @param theme The Catch color [ThemeVariant]. If no theme is set, the theme set globally on the
 * [Catch] object will be used, which defaults to [ThemeVariant.Light].
 */
@Composable
public fun CatchLogo(size: CatchLogoSize = CatchLogoSize.SMALL, theme: ThemeVariant? = null) {
    Catch.assertInitialized()
    CatchTheme(theme) {
        Image(
            painterResource(id = CatchTheme.variant.logoResId),
            contentDescription = stringResource(id = R.string.content_description_catch_logo),
            modifier = Modifier.size(size),
            contentScale = if (size == CatchLogoSize.FILL) ContentScale.FillWidth else ContentScale.Fit
        )
    }
}
