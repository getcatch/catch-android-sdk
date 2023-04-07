package com.getcatch.android.ui

internal sealed interface ActionWidgetType {
    object PurchaseConfirmation : ActionWidgetType
    object CampaignLink : ActionWidgetType
}
