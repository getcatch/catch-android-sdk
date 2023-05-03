package com.getcatch.android.models.checkout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/** The price of an item (includes the amount and currency). */
@Parcelize
@Serializable
public data class Price(
    /** The unit price of the item (in cents). */
    val amount: Int,

    /** The currency that the price of the item is in. */
    val currency: String,
) : Parcelable
