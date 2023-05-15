package com.getcatch.android.ui.activities.checkout.direct

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.android.ui.activities.checkout.CheckoutCanceledCallback

/**
 * The DirectCheckoutController manages launching and handling a direct checkout activity.
 */
public class DirectCheckoutController(
    activity: ComponentActivity,
    onConfirm: DirectCheckoutConfirmedCallback? = null,
    onCancel: CheckoutCanceledCallback? = null
) {

    private val activityResultLauncher: ActivityResultLauncher<DirectCheckoutContract.Args>

    init {
        activityResultLauncher = activity.registerForActivityResult(DirectCheckoutContract()) {
            when (it) {
                DirectCheckoutResult.Canceled -> onCancel?.onCancel()
                DirectCheckoutResult.Confirmed -> onConfirm?.onDirectCheckoutConfirmed()
                is DirectCheckoutResult.Failed -> Log.e(
                    this::class.simpleName, "Direct checkout failed.", it.error
                )
            }
        }
    }

    public constructor(
        fragment: Fragment,
        onConfirm: DirectCheckoutConfirmedCallback? = null,
        onCancel: CheckoutCanceledCallback? = null
    ) : this(
        fragment.requireActivity(), onConfirm, onCancel
    )

    /**
     * @param checkoutId The unique identifier for the checkout.
     * @param prefill Prefill values for the user.
     */
    public fun openCheckout(checkoutId: String, prefill: CheckoutPrefill) {
        val args = DirectCheckoutContract.Args(checkoutId, prefill)
        activityResultLauncher.launch(args)
    }

}
