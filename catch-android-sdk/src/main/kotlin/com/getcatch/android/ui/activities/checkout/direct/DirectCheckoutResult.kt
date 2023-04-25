package com.getcatch.android.ui.activities.checkout.direct

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The result of an attempt to complete a direct checkout
 */
public sealed class DirectCheckoutResult : Parcelable {
    @Parcelize
    public object Canceled : DirectCheckoutResult()

    @Parcelize
    public object Confirmed : DirectCheckoutResult()

    /**
     * The checkout attempt failed.
     * @param error The error encountered by the customer.
     */
    @Parcelize
    public data class Failed(
        val error: Throwable
    ) : DirectCheckoutResult()
}
