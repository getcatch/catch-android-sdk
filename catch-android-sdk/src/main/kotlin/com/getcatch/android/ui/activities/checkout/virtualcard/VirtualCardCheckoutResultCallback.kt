package com.getcatch.android.ui.activities.checkout.virtualcard

/**
 * Callback that is invoked when a [VirtualCardCheckoutResult] is available.
 */
public fun interface VirtualCardCheckoutResultCallback {
    public fun onVirtualCardCheckoutResult(virtualCardCheckoutResult: VirtualCardCheckoutResult)
}
