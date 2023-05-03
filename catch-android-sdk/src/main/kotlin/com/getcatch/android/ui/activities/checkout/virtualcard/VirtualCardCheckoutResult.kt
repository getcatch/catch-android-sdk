package com.getcatch.android.ui.activities.checkout.virtualcard

import android.os.Parcelable
import com.getcatch.android.models.checkout.CardDetails
import kotlinx.parcelize.Parcelize

public sealed class VirtualCardCheckoutResult : Parcelable {
    @Parcelize
    public object Canceled : VirtualCardCheckoutResult()

    @Parcelize
    public class Confirmed(public val cardDetails: CardDetails) : VirtualCardCheckoutResult()

    /**
     * The checkout attempt failed.
     * @param error The error encountered by the customer.
     */
    @Parcelize
    public data class Failed(
        val error: Throwable
    ) : VirtualCardCheckoutResult()
}
