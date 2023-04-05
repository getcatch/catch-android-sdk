package com.getcatch.android.ui

import androidx.compose.ui.text.TextStyle
import com.getcatch.android.ui.typography.CatchTextStyles

@Suppress("MagicNumber")
internal sealed interface InfoWidgetType {
    val logoToFontSizeRatio: Float
    val defaultTextStyle: TextStyle

    object Callout: InfoWidgetType {
        override val logoToFontSizeRatio: Float = 9f / 7f
        override val defaultTextStyle: TextStyle = CatchTextStyles.bodySmall
    }

    class PaymentMethod(variant: PaymentMethodVariant): InfoWidgetType {
        override val logoToFontSizeRatio: Float = when (variant) {
            PaymentMethodVariant.Standard -> 2f
            PaymentMethodVariant.LogoCompact -> 12f / 7f
            // The value for Compact is ignored because the logo isn't rendered
            PaymentMethodVariant.Compact -> 1f
        }

        override val defaultTextStyle: TextStyle = CatchTextStyles.bodySmall
    }

    object ExpressCheckoutCallout: InfoWidgetType {
        override val logoToFontSizeRatio: Float = 21f / 16f
        override val defaultTextStyle: TextStyle = CatchTextStyles.bodyRegular
    }
}
