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

internal val LocalCatchColorTheme =
    staticCompositionLocalOf<CatchColorTheme> { error("No catch color theme provided") }
internal val LocalColors = staticCompositionLocalOf<CatchComposeColors> { error("No colors provided") }

internal object CatchTheme {
    val colors: CatchComposeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val colorTheme: CatchColorTheme
        @Composable
        @ReadOnlyComposable
        get() = LocalCatchColorTheme.current
}

@Composable
internal fun CatchTheme(
    colorTheme: CatchColorTheme = CatchColorTheme.Light,
    defaultFontFamily: FontFamily = CatchFonts.circularFontFamily,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember {
        colorTheme.composeColors.copy()
    }.apply {
        updateColorsFrom(colorTheme.composeColors)
    }

    CompositionLocalProvider(
        LocalCatchColorTheme provides colorTheme,
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
    themeOverride: CatchColorTheme? = null,
    content: @Composable () -> Unit
) {
    CatchTheme(
        colorTheme = themeOverride ?: Catch.colorTheme.value,
        defaultFontFamily = Catch.customFontFamily.value,
        content = content
    )
}
