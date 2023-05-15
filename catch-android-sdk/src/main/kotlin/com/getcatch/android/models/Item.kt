package com.getcatch.android.models

import android.os.Parcelable
import com.getcatch.android.models.checkout.Price
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * An object specifying item details.
 */
@Parcelize
@Serializable
public data class Item(
    /** Human readable name of the item. */
    val name: String,

    /** SKU identifier of the item. */
    val sku: String,

    /** Price of the item. */
    val price: Price,

    /** Quantity of the item. */
    val quantity: Int,

    /** An optional list of categories the item belongs to. */
    val category: List<String>?,

    /** The image of the item. */
    val imageUrl: String,
) : Parcelable {
    internal fun toQueryString() = "$sku;$name;${price.amount};$quantity"
}
