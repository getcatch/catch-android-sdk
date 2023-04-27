package com.getcatch.android.ui.activities.checkout.direct

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

internal class DirectCheckoutContract :
    ActivityResultContract<DirectCheckoutContract.Args, DirectCheckoutResult>() {

    override fun createIntent(context: Context, input: Args): Intent {
        return Intent(context, DirectCheckoutActivity::class.java).putExtra(EXTRA_ARGS, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): DirectCheckoutResult {
        return intent?.parcelableExtra<Result>(EXTRA_RESULT)?.directCheckoutResult
            ?: DirectCheckoutResult.Failed(
                IllegalArgumentException("Failed to retrieve a DirectCheckoutResult.")
            )
    }


    @Parcelize
    data class Args(
        val checkoutId: String,
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
        val directCheckoutResult: DirectCheckoutResult
    ) : Parcelable {
        fun toBundle(): Bundle {
            return bundleOf(EXTRA_RESULT to this)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal companion object {
        private const val EXTRA_ARGS =
            "com.getcatch.android.ui.activities.checkout.direct.DirectCheckoutContract.extra_result"
        private const val EXTRA_RESULT =
            "com.getcatch.android.ui.activities.checkout.direct.DirectCheckoutContract.extra_result"
    }
}
