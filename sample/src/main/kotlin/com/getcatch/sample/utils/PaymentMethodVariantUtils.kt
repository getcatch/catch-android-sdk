package com.getcatch.sample.utils

import com.getcatch.android.ui.PaymentMethodVariant

val allPaymentMethodVariants = listOf(
    PaymentMethodVariant.Standard,
    PaymentMethodVariant.Compact,
    PaymentMethodVariant.LogoCompact
)

val PaymentMethodVariant.name: String
    get() = when (this) {
        PaymentMethodVariant.Standard -> "Standard"
        PaymentMethodVariant.Compact -> "Compact"
        PaymentMethodVariant.LogoCompact -> "Logo compact"
    }
