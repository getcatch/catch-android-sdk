package com.getcatch.android.models.checkout

import kotlinx.serialization.Serializable

/** Breakdown of the order total */
@Serializable
public data class Amounts(
    /** The total amount (in cents) to charge the consumer after all promotions, discounts and fees are applied. */
    val total: Int,

    /** The subtotal (in cents) that should be displayed to the consumer. */
    val subtotal: Int,

    /** The amount of tax (in cents) on the order. */
    val tax: Int,

    /** The amount of shipping cost (in cents) on the order. */
    val shipping: Int,

    /** The discount total (in cents) that should be displayed to the consumer. */
    val discountTotal: Int?,

    /** The currency in which the amounts are represented. */
    val currency: String,
)
