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

internal val LocalThemeVariant =
    staticCompositionLocalOf<ThemeVariant> { error("No theme variant provided") }
internal val LocalColors = staticCompositionLocalOf<CatchColors> { error("No colors provided") }

internal object CatchTheme {
    val colors: CatchColors
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
    variantOption: ThemeVariantOption = ThemeVariantOption.Dynamic,
    defaultFontFamily: FontFamily = CatchTypography.circularFontFamily,
    content: @Composable () -> Unit
) {
    val variant: ThemeVariant = when (variantOption) {
        is ThemeVariant -> variantOption
        ThemeVariantOption.Dynamic -> if (isSystemInDarkTheme()) {
            ThemeVariant.Dark
        } else {
            ThemeVariant.Light
        }
        ThemeVariantOption.DynamicMono -> if (isSystemInDarkTheme()) {
            ThemeVariant.DarkMono
        } else {
            ThemeVariant.LightMono
        }
    }
    val rememberedColors =
        remember { variant.colors.copy() }.apply { updateColorsFrom(variant.colors) }
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
    content: @Composable () -> Unit
) {
    CatchTheme(
        variantOption = Catch.colorTheme.value,
        defaultFontFamily = Catch.customFontFamily.value,
        content = content
    )
}
