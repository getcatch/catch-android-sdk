package com.getcatch.android.styling

public data class CatchStyleConfig(
    val textStyle: TextStyle? = null,
    val benefitTextStyle: BenefitTextStyle? = null,
    val actionButtonStyle: ActionButtonStyle? = null,
    val calloutStyle: InfoWidgetStyle? = null,
    val expressCheckoutCalloutStyle: InfoWidgetStyle? = null,
    val paymentMethodStyle: InfoWidgetStyle? = null,
    val purchaseConfirmationStyle: ActionWidgetStyle? = null,
    val campaignLinkStyle: ActionWidgetStyle? = null,
)
