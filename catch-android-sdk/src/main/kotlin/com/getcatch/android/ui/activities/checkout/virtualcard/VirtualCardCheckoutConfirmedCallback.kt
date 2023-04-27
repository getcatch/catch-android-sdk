package com.getcatch.android.ui.activities.checkout.virtualcard

import com.getcatch.android.models.checkout.CardDetails

/**
 * Callback that is invoked when a virtual card checkout is successfully confirmed.
 */
public fun interface VirtualCardCheckoutConfirmedCallback {
    public fun onVirtualCardCheckoutResult(cardDetails: CardDetails)
}
