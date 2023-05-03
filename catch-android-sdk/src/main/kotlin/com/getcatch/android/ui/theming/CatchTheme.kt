package com.getcatch.android.ui.theming

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import com.getcatch.android.Catch
import com.getcatch.android.ui.theming.color.CatchComposeColors
import com.getcatch.android.ui.typography.CatchFonts

internal val LocalThemeVariant =
    staticCompositionLocalOf<ThemeVariant> { error("No theme variant provided") }
internal val LocalColors = staticCompositionLocalOf<CatchComposeColors> { error("No colors provided") }

internal object CatchTheme {
    val colors: CatchComposeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val variant: ThemeVariant
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeVariant.current
}

@Composable
internal fun CatchTheme(
    variant: ThemeVariant = ThemeVariant.Light,
    defaultFontFamily: FontFamily = CatchFonts.circularFontFamily,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember {
        variant.composeColors.copy()
    }.apply {
        updateColorsFrom(variant.composeColors)
    }

    CompositionLocalProvider(
        LocalThemeVariant provides variant,
        LocalColors provides rememberedColors,
    ) {
        MaterialTheme(
            typography = Typography(defaultFontFamily = defaultFontFamily),
            content = content
        )
    }
}

@Composable
internal fun CatchTheme(
    themeOverride: ThemeVariant? = null,
    content: @Composable () -> Unit
) {
    CatchTheme(
        variant = themeOverride ?: Catch.colorTheme.value,
        defaultFontFamily = Catch.customFontFamily.value,
        content = content
    )
}
