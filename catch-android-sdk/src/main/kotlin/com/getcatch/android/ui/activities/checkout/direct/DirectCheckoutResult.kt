package com.getcatch.android.ui.activities.checkout.direct

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

internal sealed class DirectCheckoutResult : Parcelable {
    @Parcelize
    object Canceled : DirectCheckoutResult()

    @Parcelize
    object Confirmed : DirectCheckoutResult()

    @Parcelize
    data class Failed(
        val error: Throwable
    ) : DirectCheckoutResult()
}
