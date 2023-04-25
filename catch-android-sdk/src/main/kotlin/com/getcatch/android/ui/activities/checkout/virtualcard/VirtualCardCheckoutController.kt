package com.getcatch.android.ui.activities.checkout.virtualcard

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.getcatch.android.models.checkout.CheckoutPrefill

public class VirtualCardCheckoutController(
    activity: ComponentActivity,
    callback: VirtualCardCheckoutResultCallback
) {
    private val activityResultLauncher: ActivityResultLauncher<VirtualCardCheckoutContract.Args>

    init {
        activityResultLauncher = activity.registerForActivityResult(VirtualCardCheckoutContract()) {
            callback.onVirtualCardCheckoutResult(it)
        }
    }

    public constructor(fragment: Fragment, callback: VirtualCardCheckoutResultCallback) : this(
        fragment.requireActivity(), callback
    )

    /**
     * @param checkoutId The unique identifier for the checkout.
     * @param prefill Prefill values for the user.
     */
    public fun openCheckout(checkoutId: String, prefill: CheckoutPrefill) {
        val args = VirtualCardCheckoutContract.Args(checkoutId, prefill)
        activityResultLauncher.launch(args)
    }
}
