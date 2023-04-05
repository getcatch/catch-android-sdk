package com.getcatch.android.ui

/** Represents the configuration variants of the `PaymentMethod` widget. */
public sealed interface PaymentMethodVariant {
    /** The default configuration which includes the Catch logo, filler text and reward text. */
    public object Standard: PaymentMethodVariant

    /** The compact variant removes the Catch logo from the default configuration. */
    public object Compact: PaymentMethodVariant

    /** The logoCompact variant removes the filler text from the default configuration. */
    public object LogoCompact: PaymentMethodVariant
}
