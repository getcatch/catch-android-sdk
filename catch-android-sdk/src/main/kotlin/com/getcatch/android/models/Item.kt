package com.getcatch.android.models

import android.os.Parcelable
import com.getcatch.android.models.checkout.Price
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
public data class Item(
    val name: String,
    val sku: String,
    val price: Price,
    val quantity: Int,
    val category: List<String>?,
    val imageUrl: String,
) : Parcelable {
    internal fun toQueryString() = "$sku;$name;${price.amount};$quantity"
}
