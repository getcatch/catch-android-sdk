package com.getcatch.android.ui.activities.checkout.direct

/**
 * Callback that is invoked when a [DirectCheckoutResult] is available.
 */
public fun interface DirectCheckoutResultCallback {
    public fun onDirectCheckoutResult(directCheckoutResult: DirectCheckoutResult)
}
