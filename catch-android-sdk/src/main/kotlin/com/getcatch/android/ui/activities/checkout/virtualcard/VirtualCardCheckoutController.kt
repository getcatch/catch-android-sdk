package com.getcatch.android.ui.activities.checkout.virtualcard

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.android.models.checkout.CreateVirtualCardCheckoutBody
import com.getcatch.android.ui.activities.checkout.CheckoutCanceledCallback

public class VirtualCardCheckoutController(
    activity: ComponentActivity,
    onConfirm: VirtualCardCheckoutConfirmedCallback? = null,
    onCancel: CheckoutCanceledCallback? = null,
) {
    private val activityResultLauncher: ActivityResultLauncher<VirtualCardCheckoutContract.Args>

    init {
        activityResultLauncher = activity.registerForActivityResult(VirtualCardCheckoutContract()) {
            when (it) {
                VirtualCardCheckoutResult.Canceled -> onCancel?.onCancel()
                is VirtualCardCheckoutResult.Confirmed -> onConfirm?.onVirtualCardCheckoutResult(it.cardDetails)
                is VirtualCardCheckoutResult.Failed -> Log.e(
                    this::class.simpleName,
                    "Virtual card checkout failed.",
                    it.error
                )
            }
        }
    }

    public constructor(
        fragment: Fragment,
        onConfirm: VirtualCardCheckoutConfirmedCallback? = null,
        onCancel: CheckoutCanceledCallback? = null
    ) : this(
        fragment.requireActivity(), onConfirm, onCancel
    )

    /**
     * @param checkoutId The unique identifier for the checkout obtained by making
     * a request to the POST /v1/virtual_card/checkouts endpoint.
     * @param prefill Prefill values for the user.
     */
    public fun openCheckout(checkoutId: String, prefill: CheckoutPrefill) {
        val args = VirtualCardCheckoutContract.Args(
            checkoutData = null,
            checkoutId = checkoutId,
            prefill
        )
        activityResultLauncher.launch(args)
    }

    /**
     * @param checkoutData The unique identifier for the order that will be used to create a checkout.
     * @param prefill Prefill values for the user.
     */
    public fun createAndOpenCheckout(checkoutData: CreateVirtualCardCheckoutBody, prefill: CheckoutPrefill) {
        val args = VirtualCardCheckoutContract.Args(
            checkoutData = checkoutData,
            checkoutId = null,
            prefill
        )
        activityResultLauncher.launch(args)
    }
}
