package com.getcatch.android.utils

import android.content.res.TypedArray
import android.util.Log
import androidx.annotation.StyleableRes
import com.getcatch.android.R
import com.getcatch.android.styling.values.ColorValue
import com.getcatch.android.theming.CalloutBorderStyle
import com.getcatch.android.theming.ThemeVariant

internal fun TypedArray.getHasOrPrefix() = getBoolean(R.styleable.CalloutView_hasOrPrefix, false)

internal object CalloutBorderStyleAttrEnum {
    const val SQUARE = 0
    const val SLIGHT_ROUND = 1
    const val PILL = 2
    const val CUSTOM = 3
}

internal object CalloutThemeVariantAttrEnum {
    const val LIGHT = 0
    const val LIGHT_MONO = 1
    const val DARK = 2
    const val DARK_MONO = 3
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
        else -> null
    }

internal fun TypedArray.getThemeVariant(@StyleableRes styleableResId: Int): ThemeVariant? =
    when (getInt(styleableResId, -1)) {
        CalloutThemeVariantAttrEnum.LIGHT -> ThemeVariant.Light
        CalloutThemeVariantAttrEnum.LIGHT_MONO -> ThemeVariant.LightMono
        CalloutThemeVariantAttrEnum.DARK -> ThemeVariant.Dark
        CalloutThemeVariantAttrEnum.DARK_MONO -> ThemeVariant.DarkMono
        else -> null
    }


