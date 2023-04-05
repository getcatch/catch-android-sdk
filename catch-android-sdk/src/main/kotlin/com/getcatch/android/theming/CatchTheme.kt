package com.getcatch.android.theming

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import com.getcatch.android.Catch
import com.getcatch.android.theming.color.CatchComposeColors
import com.getcatch.android.ui.typography.CatchFonts

internal val LocalThemeVariant =
    staticCompositionLocalOf<ThemeVariant> { error("No theme variant provided") }
internal val LocalColors = staticCompositionLocalOf<CatchComposeColors> { error("No colors provided") }

internal object CatchTheme {
    val colors: CatchComposeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

//    val typography: CatchTypography
//        @Composable
//        @ReadOnlyComposable
//        get() = LocalTypography.current

    val variant: ThemeVariant
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeVariant.current
}

@Composable
internal fun CatchTheme(
    variantOption: ThemeVariantOption = DynamicThemeVariant.Standard,
    defaultFontFamily: FontFamily = CatchFonts.circularFontFamily,
    content: @Composable () -> Unit
) {
    val variant: ThemeVariant = when (variantOption) {
        is ThemeVariant -> variantOption
        is DynamicThemeVariant -> if (isSystemInDarkTheme()) {
            variantOption.darkVariant
        } else {
            variantOption.lightVariant
        }
    }

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
    themeOverride: ThemeVariantOption? = null,
    content: @Composable () -> Unit
) {
    CatchTheme(
        variantOption = themeOverride ?: Catch.colorTheme.value,
        defaultFontFamily = Catch.customFontFamily.value,
        content = content
    )
}
