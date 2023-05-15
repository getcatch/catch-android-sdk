package com.getcatch.android.ui.activities.checkout.virtualcard

import android.os.Parcelable
import com.getcatch.android.models.checkout.CardDetails
import kotlinx.parcelize.Parcelize

internal sealed class VirtualCardCheckoutResult : Parcelable {
    @Parcelize
    object Canceled : VirtualCardCheckoutResult()

    @Parcelize
    class Confirmed(val cardDetails: CardDetails) : VirtualCardCheckoutResult()

    @Parcelize
    data class Failed(
        val error: Throwable
    ) : VirtualCardCheckoutResult()
}
