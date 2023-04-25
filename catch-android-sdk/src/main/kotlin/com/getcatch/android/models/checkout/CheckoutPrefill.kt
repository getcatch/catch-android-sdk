package com.getcatch.android.models.checkout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Specifies prefill values to use in the checkout flow.
 *
 * Applications should always try to provide as much prefill data as possible.
 * However, all prefill entries are individually optional.
 */
@Parcelize
public data class CheckoutPrefill(
    /** The phone number to prefill in the checkout flow. */
    val userPhone : String? = null,

    /** The consumer name to prefill in the checkout flow. */
    val userName: String? = null,

    /** The email to prefill in the checkout flow. */
    val userEmail: String? = null,
) : Parcelable
