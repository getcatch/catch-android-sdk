package com.getcatch.android.ui.styles

/**
 * The global styles that can be used to customize Catch widgets.
 *
 * Any styling defined at the widget-level (ex. calloutStyle) will override styling defined globally
 * (ex. widgetTextStyle). Additionally, any themes or overrides set on an individual widget will
 * override the global configurations set here. All styling parameters are optional and only
 * non-null values will override those at a higher level.
 */
public data class CatchStyleConfig(
    /** Configures the styling for text components across all widgets. */
    val widgetTextStyle: WidgetTextStyle? = null,

    /** Configures the styling for benefit text components across all widgets. */
    val benefitTextStyle: BenefitTextStyle? = null,

    /** Configures the styling for action buttons across all widgets. */
    val actionButtonStyle: ActionButtonStyle? = null,

    /** Configures the styling for all [Callout] widgets. */
    val calloutStyle: InfoWidgetStyle? = null,

    /** Configures the styling for all [ExpressCheckoutCallout] widgets. */
    val expressCheckoutCalloutStyle: InfoWidgetStyle? = null,

    /** Configures the styling for all [PaymentMethod] widgets. */
    val paymentMethodStyle: InfoWidgetStyle? = null,

    /** Configures the styling for all [PurchaseConfirmation] widgets. */
    val purchaseConfirmationStyle: ActionWidgetStyle? = null,

    /** Configures the styling for all [CampaignLink] widgets. */
    val campaignLinkStyle: ActionWidgetStyle? = null,
)
