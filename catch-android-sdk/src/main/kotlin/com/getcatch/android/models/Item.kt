package com.getcatch.android.models

import com.getcatch.android.models.checkout.Price

public data class Item(
    val name: String,
    val sku: String,
    val price: Price,
    val quantity: Int,
    val category: List<String>?,
    val imageUrl: String,
) {
    internal fun toQueryString() = "$sku;$name;${price.amount};$quantity"
}
