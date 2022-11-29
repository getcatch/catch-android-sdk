package com.getcatch.android.theming

import androidx.compose.ui.graphics.Color

internal object StandardThemeConfigs {
    internal val Dark by lazy {
        ThemeConfig(
            baseColors = darkColors,
            typescale = standardTypescale,
            styleTokens = ThemeConfig.StyleTokens(),
        )
    }

    internal val DarkMono by lazy {
        ThemeConfig(
            baseColors = darkColors,
            typescale = standardTypescale,
            styleTokens = ThemeConfig.StyleTokens(
                cartEarningCreditsText = ThemeConfig.BaseColorKey.WHITE,
                cartRedeemingCreditsText = ThemeConfig.BaseColorKey.WHITE,
            ),
        )
    }
    internal val Light by lazy {
        ThemeConfig(
            baseColors = lightColors,
            typescale = standardTypescale,
            styleTokens = ThemeConfig.StyleTokens(),
        )
    }

    internal val LightMono by lazy {
        ThemeConfig(
            baseColors = lightColors,
            typescale = standardTypescale,
            styleTokens = ThemeConfig.StyleTokens(
                cartEarningCreditsText = ThemeConfig.BaseColorKey.BLACK,
                cartRedeemingCreditsText = ThemeConfig.BaseColorKey.BLACK,
            ),
        )
    }

    private val lightColors by lazy {
        ThemeConfig.BaseColors(
            white = Color(CatchRawColors.WHITE),
            black = Color(CatchRawColors.BLACK),
            grey1 = Color(CatchRawColors.GREY_1),
            grey2 = Color(CatchRawColors.GREY_2),
            grey3 = Color(CatchRawColors.GREY_3),
            grey4 = Color(CatchRawColors.GREY_4),
            grey5 = Color(CatchRawColors.GREY_5),
            grey6 = Color(CatchRawColors.GREY_6),
            grey7 = Color(CatchRawColors.GREY_7),
            primaryMain = Color(CatchRawColors.PINK_2),
            primaryLight = Color(CatchRawColors.PINK_1),
            secondaryMain = Color(CatchRawColors.GREEN_2),
            secondaryLight = Color(CatchRawColors.GREEN_1),
        )
    }

    private val darkColors by lazy {
        ThemeConfig.BaseColors(
            white = Color(CatchRawColors.WHITE),
            black = Color(CatchRawColors.BLACK),
            grey1 = Color(CatchRawColors.GREY_1),
            grey2 = Color(CatchRawColors.GREY_2),
            grey3 = Color(CatchRawColors.GREY_3),
            grey4 = Color(CatchRawColors.GREY_4),
            grey5 = Color(CatchRawColors.GREY_5),
            grey6 = Color(CatchRawColors.GREY_6),
            grey7 = Color(CatchRawColors.GREY_7),
            primaryMain = Color(CatchRawColors.DARK_MODE_PINK),
            primaryLight = Color(CatchRawColors.PINK_1),
            secondaryMain = Color(CatchRawColors.DARK_MODE_GREEN),
            secondaryLight = Color(CatchRawColors.GREEN_1),
        )
    }

    private val standardTypescale = ThemeConfig.Typescale()
}
