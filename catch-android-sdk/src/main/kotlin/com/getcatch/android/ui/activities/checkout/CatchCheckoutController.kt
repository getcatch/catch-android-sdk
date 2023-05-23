package com.getcatch.android.ui.activities.checkout

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.android.models.checkout.CreateVirtualCardCheckoutData
import com.getcatch.android.ui.activities.checkout.direct.DirectCheckoutContract
import com.getcatch.android.ui.activities.checkout.direct.DirectCheckoutResult
import com.getcatch.android.ui.activities.checkout.virtualcard.VirtualCardCheckoutContract
import com.getcatch.android.ui.activities.checkout.virtualcard.VirtualCardCheckoutResult

/**
 * The CatchCheckoutController manages launching and handling checkout activities.
 */
public class CatchCheckoutController(
    activity: ComponentActivity,
    onCheckoutConfirmed: CheckoutConfirmedCallback? = null,
    onVirtualCardCheckoutConfirmed: VirtualCardCheckoutConfirmedCallback? = null,
    onCancel: CheckoutCanceledCallback? = null
) {

    private val directCheckoutActivityResultLauncher: ActivityResultLauncher<DirectCheckoutContract.Args>
    private val virtualCardCheckoutActivityResultLauncher: ActivityResultLauncher<VirtualCardCheckoutContract.Args>

    init {
        directCheckoutActivityResultLauncher =
            activity.registerForActivityResult(DirectCheckoutContract()) {
                when (it) {
                    DirectCheckoutResult.Canceled -> onCancel?.onCancel()
                    DirectCheckoutResult.Confirmed -> onCheckoutConfirmed?.onCheckoutConfirmed()
                    is DirectCheckoutResult.Failed -> Log.e(
                        this::class.simpleName, "Direct checkout failed.", it.error
                    )
                }
            }
        virtualCardCheckoutActivityResultLauncher =
            activity.registerForActivityResult(VirtualCardCheckoutContract()) {
                when (it) {
                    VirtualCardCheckoutResult.Canceled -> onCancel?.onCancel()
                    is VirtualCardCheckoutResult.Confirmed -> {
                        onVirtualCardCheckoutConfirmed?.onVirtualCardCheckoutConfirmed(
                            it.cardDetails
                        )
                    }

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
        onCheckoutConfirmed: CheckoutConfirmedCallback? = null,
        onVirtualCardCheckoutConfirmed: VirtualCardCheckoutConfirmedCallback? = null,
        onCancel: CheckoutCanceledCallback? = null
    ) : this(
        fragment.requireActivity(),
        onCheckoutConfirmed,
        onVirtualCardCheckoutConfirmed,
        onCancel
    )

    /**
     * @param checkoutId The unique identifier for the checkout.
     * @param prefill Prefill values for the user.
     */
    public fun openCheckout(checkoutId: String, prefill: CheckoutPrefill) {
        val args = DirectCheckoutContract.Args(checkoutId, prefill)
        directCheckoutActivityResultLauncher.launch(args)
    }

    /**
     * @param checkoutId The unique identifier for the checkout obtained by making
     * a request to the POST /v1/virtual_card/checkouts endpoint.
     * @param prefill Prefill values for the user.
     */
    public fun openVirtualCardCheckout(checkoutId: String, prefill: CheckoutPrefill) {
        val args = VirtualCardCheckoutContract.Args(
            checkoutData = null,
            checkoutId = checkoutId,
            prefill
        )
        virtualCardCheckoutActivityResultLauncher.launch(args)
    }

    /**
     * @param checkoutData The order data for the checkout.
     * @param prefill Prefill values for the user.
     */
    public fun createAndOpenVirtualCardCheckout(
        checkoutData: CreateVirtualCardCheckoutData,
        prefill: CheckoutPrefill
    ) {
        val args = VirtualCardCheckoutContract.Args(
            checkoutData = checkoutData,
            checkoutId = null,
            prefill
        )
        virtualCardCheckoutActivityResultLauncher.launch(args)
    }
}
