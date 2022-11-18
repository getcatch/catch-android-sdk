package com.getcatch.android.theming

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.getcatch.android.theming.atomization.FontWeight
import com.getcatch.android.utils.fromHexString

internal data class ThemeConfig(
    val baseColors: BaseColors,
    val typescale: Typescale,
    val styleTokens: StyleTokens,
) {

    data class BaseColors(
        val white: Color,
        val black: Color,
        val grey1: Color,
        val grey2: Color,
        val grey3: Color,
        val grey4: Color,
        val grey5: Color,
        val grey6: Color,
        val grey7: Color,
        val primaryMain: Color,
        val primaryLight: Color,
        val secondaryMain: Color,
        val secondaryLight: Color,
    ) {
        constructor(
            white: String,
            black: String,
            grey1: String,
            grey2: String,
            grey3: String,
            grey4: String,
            grey5: String,
            grey6: String,
            grey7: String,
            primaryMain: String,
            primaryLight: String,
            secondaryMain: String,
            secondaryLight: String,
        ) : this(
            white = Color.fromHexString(white),
            black = Color.fromHexString(black),
            grey1 = Color.fromHexString(grey1),
            grey2 = Color.fromHexString(grey2),
            grey3 = Color.fromHexString(grey3),
            grey4 = Color.fromHexString(grey4),
            grey5 = Color.fromHexString(grey5),
            grey6 = Color.fromHexString(grey6),
            grey7 = Color.fromHexString(grey7),
            primaryMain = Color.fromHexString(primaryMain),
            primaryLight = Color.fromHexString(primaryLight),
            secondaryMain = Color.fromHexString(secondaryMain),
            secondaryLight = Color.fromHexString(secondaryLight),
        )
    }

    data class Typescale(
        val heading1: TypescaleVariant = TypescaleVariant(
            fontSize = 26,
            lineHeight = 32,
            fontWeight = FontWeight.W700,
            letterSpacing = -0.16f,
        ),
        val heading2: TypescaleVariant = TypescaleVariant(
            fontSize = 26,
            lineHeight = 32,
            fontWeight = FontWeight.W700,
            letterSpacing = -0.16f,
        ),
        val heading3: TypescaleVariant = TypescaleVariant(
            fontSize = 20,
            lineHeight = 28,
            fontWeight = FontWeight.W700,
        ),
        val heading4: TypescaleVariant = TypescaleVariant(
            fontSize = 16,
            lineHeight = 24,
            fontWeight = FontWeight.W700,
        ),
        val bodyLarge: TypescaleVariant = TypescaleVariant(
            fontSize = 14,
            lineHeight = 20,
            fontWeight = FontWeight.W700,
        ),
        val bodyRegular: TypescaleVariant = TypescaleVariant(
            fontSize = 18,
            lineHeight = 28,
            fontWeight = FontWeight.W400,
        ),
        val bodySmall: TypescaleVariant = TypescaleVariant(
            fontSize = 16,
            lineHeight = 24,
            fontWeight = FontWeight.W400,
        ),
        val linkLarge: TypescaleVariant = TypescaleVariant(
            fontSize = 14,
            lineHeight = 20,
            fontWeight = FontWeight.W400,
            underline = true,
        ),
        val linkRegular: TypescaleVariant = TypescaleVariant(
            fontSize = 18,
            lineHeight = 28,
            fontWeight = FontWeight.W400,
            underline = true,
        ),
        val linkSmall: TypescaleVariant = TypescaleVariant(
            fontSize = 16,
            lineHeight = 24,
            fontWeight = FontWeight.W500,
            underline = true,
        ),
        val buttonLabel: TypescaleVariant = TypescaleVariant(
            fontSize = 14,
            lineHeight = 20,
            fontWeight = FontWeight.W500,
        ),
        val buttonLabelCompact: TypescaleVariant = TypescaleVariant(
            fontSize = 18,
            lineHeight = 28,
            fontWeight = FontWeight.W500,
        ),
    ) {
        data class TypescaleVariant(
            val fontSize: Int,
            val lineHeight: Int,
            val fontWeight: FontWeight,
            val letterSpacing: Float? = null,
            val underline: Boolean = false,
        ) {
            val textDecoration: TextDecoration
                get() = if (underline) TextDecoration.Underline else TextDecoration.None

            fun toTextStyle() = TextStyle(
                fontSize = fontSize.sp,
                lineHeight = lineHeight.sp,
                fontWeight = fontWeight.toComposeFontWeight(),
                letterSpacing = letterSpacing?.sp ?: TextUnit.Unspecified,
                textDecoration = textDecoration,
            )
        }
    }

    enum class BaseColorKey {
        WHITE,
        BLACK,
        GREY_1,
        GREY_2,
        GREY_3,
        GREY_4,
        GREY_5,
        GREY_6,
        GREY_7,
        PRIMARY_MAIN,
        PRIMARY_LIGHT,
        SECONDARY_MAIN,
        SECONDARY_LIGHT;
    }

    enum class WidgetSizeVariant {
        DEFAULT,
        SMALL;
    }

    data class StyleTokens(
        // Borders
        val buttonBorderRadius: Int = DEFAULT_BUTTON_BORDER_RADIUS,
        // Color mapping
        val primaryText: BaseColorKey = BaseColorKey.GREY_7,
        val secondaryText: BaseColorKey = BaseColorKey.GREY_6,
        val tertiaryText: BaseColorKey = BaseColorKey.GREY_5,
        val regularButtonBackground: BaseColorKey = BaseColorKey.PRIMARY_MAIN,
        val regularButtonText: BaseColorKey = BaseColorKey.WHITE,
        val cartEarningCreditsText: BaseColorKey = BaseColorKey.PRIMARY_MAIN,
        val cartRedeemingCreditsText: BaseColorKey = BaseColorKey.SECONDARY_MAIN,
        val cartRedeemingCreditsBackground: BaseColorKey = BaseColorKey.SECONDARY_LIGHT,
        val primaryStroke: BaseColorKey = BaseColorKey.GREY_3,
        // Catch widget specific
        val widgetAccent: BaseColorKey = BaseColorKey.SECONDARY_MAIN,
        val widgetLink: BaseColorKey = BaseColorKey.PRIMARY_MAIN,
        val widgetSizeVariant: WidgetSizeVariant = WidgetSizeVariant.DEFAULT,
    )

    private fun getColorValue(key: BaseColorKey): Color = when (key) {
        BaseColorKey.WHITE -> baseColors.white
        BaseColorKey.BLACK -> baseColors.black
        BaseColorKey.GREY_1 -> baseColors.grey1
        BaseColorKey.GREY_2 -> baseColors.grey2
        BaseColorKey.GREY_3 -> baseColors.grey3
        BaseColorKey.GREY_4 -> baseColors.grey4
        BaseColorKey.GREY_5 -> baseColors.grey5
        BaseColorKey.GREY_6 -> baseColors.grey6
        BaseColorKey.GREY_7 -> baseColors.grey7
        BaseColorKey.PRIMARY_MAIN -> baseColors.primaryMain
        BaseColorKey.PRIMARY_LIGHT -> baseColors.primaryLight
        BaseColorKey.SECONDARY_MAIN -> baseColors.secondaryMain
        BaseColorKey.SECONDARY_LIGHT -> baseColors.secondaryLight
    }

    // COLOR STRINGS
    val primaryTextColor: Color = getColorValue(styleTokens.primaryText)
    val secondaryTextColor: Color = getColorValue(styleTokens.secondaryText)
    val tertiaryTextColor: Color = getColorValue(styleTokens.tertiaryText)
    val regularButtonBackgroundColor: Color = getColorValue(styleTokens.regularButtonBackground)
    val regularButtonTextColor: Color = getColorValue(styleTokens.regularButtonText)
    val cartEarningCreditsTextColor: Color = getColorValue(styleTokens.cartEarningCreditsText)
    val cartRedeemingCreditsTextColor: Color = getColorValue(styleTokens.cartRedeemingCreditsText)
    val cartRedeemingCreditsBackgroundColor: Color =
        getColorValue(styleTokens.cartRedeemingCreditsBackground)
    val primaryStrokeColor: Color = getColorValue(styleTokens.primaryStroke)
    val widgetAccentColor: Color = getColorValue(styleTokens.widgetAccent)
    val widgetLinkColor: Color = getColorValue(styleTokens.widgetLink)

    companion object {
        const val DEFAULT_BUTTON_BORDER_RADIUS = 4
    }
}
