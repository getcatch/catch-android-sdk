package com.getcatch.android.ui.activities.checkout.virtualcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.android.utils.parcelableExtra
import kotlinx.parcelize.Parcelize

internal class VirtualCardCheckoutContract :
    ActivityResultContract<VirtualCardCheckoutContract.Args, VirtualCardCheckoutResult>() {

    override fun createIntent(context: Context, input: Args): Intent {
        return Intent(context, VirtualCardCheckoutActivity::class.java).putExtra(EXTRA_ARGS, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): VirtualCardCheckoutResult {
        return intent?.parcelableExtra<Result>(EXTRA_RESULT)?.virtualCardCheckoutResult
            ?: VirtualCardCheckoutResult.Failed(
                IllegalArgumentException("Failed to retrieve a VirtualCardCheckoutResult.")
            )
    }


    @Parcelize
    data class Args(
        val orderId: String,
        val prefill: CheckoutPrefill,
    ) : Parcelable {
        companion object {
            fun fromIntent(intent: Intent): Args? {
                return intent.parcelableExtra(EXTRA_ARGS)
            }
        }
    }

    @Parcelize
    data class Result(
        val virtualCardCheckoutResult: VirtualCardCheckoutResult
    ) : Parcelable {
        fun toBundle(): Bundle {
            return bundleOf(EXTRA_RESULT to this)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal companion object {
        private const val EXTRA_ARGS =
            "com.getcatch.android.ui.activities.checkout.virtualcard.VirtualCardCheckoutContract.extra_args"
        private const val EXTRA_RESULT =
            "com.getcatch.android.ui.activities.checkout.virtualcard.VirtualCardCheckoutContract.extra_result"
    }
}
