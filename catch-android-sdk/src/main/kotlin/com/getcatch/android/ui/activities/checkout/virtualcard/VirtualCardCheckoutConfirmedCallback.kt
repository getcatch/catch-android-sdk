package com.getcatch.android.ui.activities.checkout.virtualcard

import com.getcatch.android.models.checkout.CardDetails

/**
 * Callback that is invoked when a virtual card checkout is successfully confirmed.
 */
public fun interface VirtualCardCheckoutConfirmedCallback {
    /** The call signature for the callback. */
    public fun onVirtualCardCheckoutConfirmed(cardDetails: CardDetails)
}
