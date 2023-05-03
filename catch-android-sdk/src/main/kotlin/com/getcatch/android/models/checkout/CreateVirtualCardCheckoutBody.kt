package com.getcatch.android.models.checkout

import android.os.Parcelable
import com.getcatch.android.models.Item
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
public data class CreateVirtualCardCheckoutBody(
    /**
     * The ID of this order in the merchant's system which Catch will store
     * for shared identification purposes. This ID should be unique per order.
     */
    val merchantOrderId: String,

    /** The merchant's public API key. */
    val merchantPublicKey: String,

    /** Contains details of the order total. */
    val amounts: Amounts,

    /** Contains details of the billing contact. */
    val billing: Address,

    /** Contains details of the shipping contact. */
    val shipping: Address,

    /** The name of the shipping method (e.g., "express"). */
    val shippingMethod: String?,

    /** Contains details of the order's items. */
    val items: List<Item>,

    /**
     * The ID of the consumer in the merchant's system which Catch will store for shared identification purposes.
     * (While this field is required, the value null may be explicitly passed if the consumer is anonymous and
     * doesn't have an ID. However, it is recommended to provide an ID wherever possible).
     */
    val merchantUserId: String?,

    /** User cohorts that this checkout should be associated with. */
    val userCohorts: List<String>,
) : Parcelable {
    /** Contains the name and version of the platform in use. */
    internal val platform = Platform
}


