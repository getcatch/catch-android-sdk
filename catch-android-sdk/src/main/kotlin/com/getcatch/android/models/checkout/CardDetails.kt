package com.getcatch.android.models.checkout

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/** Contains payment card details. */
@Serializable
@Parcelize
public data class CardDetails(
    /** The card number. */
    val cardNumber: String,

    /** The year the card expires. */
    val expirationYear: String,

    /** The month the card expires. */
    val expirationMonth: String,

    /** The three or four digit security code. */
    val cvc: String,

    /** The billing zipcode associated with the card. */
    val zipCode: String,
) : Parcelable
