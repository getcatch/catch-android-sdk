package com.getcatch.android.utils

import android.content.res.TypedArray
import android.util.Log
import androidx.annotation.StyleableRes
import com.getcatch.android.R
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.CatchLogoSize
import com.getcatch.android.ui.PaymentMethodVariant
import com.getcatch.android.ui.styles.values.ColorValue
import com.getcatch.android.ui.theming.CatchColorTheme

internal fun TypedArray.getHasOrPrefix() = getBoolean(R.styleable.CalloutView_hasOrPrefix, false)

internal object CalloutBorderStyleAttrEnum {
    const val SQUARE = 0
    const val SLIGHT_ROUND = 1
    const val PILL = 2
    const val CUSTOM = 3
    const val NONE = 4
}

internal object BorderStyleAttrEnum {
    const val SQUARE = 0
    const val SLIGHT_ROUND = 1
    const val CUSTOM = 2
    const val NONE = 3
}

internal object CatchColorThemeAttrEnum {
    const val LIGHT = 0
    const val LIGHT_MONO = 1
    const val DARK = 2
    const val DARK_MONO = 3
}

internal object PaymentMethodVariantAttrEnum {
    const val STANDARD = 0
    const val COMPACT = 1
    const val LOGO_COMPACT = 2
}

internal object CatchLogoSizeAttrEnum {
    const val SMALL = 0
    const val MEDIUM = 1
    const val FILL = 2
}

internal fun TypedArray.getCalloutBorderStyle(): CalloutBorderStyle? =
    when (getInt(R.styleable.CalloutView_calloutBorderStyle, -1)) {
        CalloutBorderStyleAttrEnum.SQUARE -> CalloutBorderStyle.Square
        CalloutBorderStyleAttrEnum.SLIGHT_ROUND -> CalloutBorderStyle.SlightRound
        CalloutBorderStyleAttrEnum.PILL -> CalloutBorderStyle.Pill
        CalloutBorderStyleAttrEnum.CUSTOM -> {
            val radius = getInt(R.styleable.CalloutView_customBorderRadius, -1)
            val color = getColor(R.styleable.CalloutView_customBorderColor, -1)
            if (radius != -1 && color != -1) {
                CalloutBorderStyle.Custom(
                    radius = radius.toFloat(),
                    color = ColorValue(color),
                )
            } else {
                Log.w(
                    "CalloutView",
                    "Border style set to custom, but radius or color not specified."
                )
                null
            }
        }
        CalloutBorderStyleAttrEnum.NONE -> CalloutBorderStyle.None
        else -> null
    }


internal fun TypedArray.getBorderStyle(
    widgetName: String,
    @StyleableRes borderStyleResId: Int,
    @StyleableRes customBorderColorResId: Int,
    @StyleableRes customBorderRadiusResId: Int,
): BorderStyle? =
    when (getInt(borderStyleResId, -1)) {
        BorderStyleAttrEnum.SQUARE -> BorderStyle.Square
        BorderStyleAttrEnum.SLIGHT_ROUND -> BorderStyle.SlightRound
        BorderStyleAttrEnum.CUSTOM -> {
            val radius = getInt(customBorderRadiusResId, -1)
            val color = getColor(customBorderColorResId, -1)
            if (radius != -1 && color != -1) {
                BorderStyle.Custom(
                    radius = radius.toFloat(),
                    color = ColorValue(color),
                )
            } else {
                Log.w(
                    widgetName,
                    "Border style set to custom, but radius or color not specified."
                )
                null
            }
        }
        BorderStyleAttrEnum.NONE -> BorderStyle.None
        else -> null
    }

internal fun TypedArray.getCatchColorTheme(@StyleableRes styleableResId: Int): CatchColorTheme? =
    when (getInt(styleableResId, -1)) {
        CatchColorThemeAttrEnum.LIGHT -> CatchColorTheme.Light
        CatchColorThemeAttrEnum.LIGHT_MONO -> CatchColorTheme.LightMono
        CatchColorThemeAttrEnum.DARK -> CatchColorTheme.Dark
        CatchColorThemeAttrEnum.DARK_MONO -> CatchColorTheme.DarkMono
        else -> null
    }

internal fun TypedArray.getPaymentMethodVariant(): PaymentMethodVariant? =
    when (getInt(R.styleable.PaymentMethodView_paymentMethodVariant, -1)) {
        PaymentMethodVariantAttrEnum.STANDARD -> PaymentMethodVariant.Standard
        PaymentMethodVariantAttrEnum.COMPACT -> PaymentMethodVariant.Compact
        PaymentMethodVariantAttrEnum.LOGO_COMPACT -> PaymentMethodVariant.LogoCompact
        else -> null
    }

internal fun TypedArray.getCampaignName(): String? =
    getString(R.styleable.CampaignLinkView_campaignName)

internal fun TypedArray.getCatchLogoSize(): CatchLogoSize? =
    when (getInt(R.styleable.CatchLogoView_catchLogoSize, -1)) {
        CatchLogoSizeAttrEnum.SMALL -> CatchLogoSize.SMALL
        CatchLogoSizeAttrEnum.MEDIUM -> CatchLogoSize.MEDIUM
        CatchLogoSizeAttrEnum.FILL -> CatchLogoSize.FILL
        else -> null
    }
