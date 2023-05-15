package com.getcatch.android.ui.activities.checkout

/**
 * Callback that is invoked when a direct checkout is canceled by the user.
 */
public fun interface CheckoutCanceledCallback {
    /** The call signature for the callback. */
    public fun onCancel()
}
