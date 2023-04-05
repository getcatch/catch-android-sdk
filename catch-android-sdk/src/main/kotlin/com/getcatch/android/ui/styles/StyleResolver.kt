package com.getcatch.android.ui.styles

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.getcatch.android.Catch
import com.getcatch.android.ui.theming.ThemeVariant

internal object StyleResolver {

    fun calloutStyles(
        theme: ThemeVariant,
        instanceOverrides: InfoWidgetStyle?,
    ): InfoWidgetStyle.Resolved {
        val defaults = InfoWidgetStyle.Resolved(
            widgetTextStyle = WidgetTextStyle.Resolved(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontColor = theme.composeColors.foreground,
                fontWeight = FontWeight.W400,
            ),
            benefitTextStyle = BenefitTextStyle.Resolved(
                fontWeight = FontWeight.W700,
                earnFontColor = theme.composeColors.accent,
                redeemFontColor = theme.composeColors.secondaryAccent,
            )
        )
        val globalOverrides = Catch.styleConfig.let { globalConfig ->
            val basicOverrides = InfoWidgetStyle(
                widgetTextStyle = globalConfig?.widgetTextStyle,
                benefitTextStyle = globalConfig?.benefitTextStyle,
            )
            return@let basicOverrides.withOverrides(globalConfig?.calloutStyle)
        }
        return defaults
            .withOverrides(globalOverrides)
            .withOverrides(instanceOverrides)
    }
}