package com.getcatch.sample.utils

import com.getcatch.android.models.Item
import com.getcatch.android.models.checkout.Address
import com.getcatch.android.models.checkout.Amounts
import com.getcatch.android.models.checkout.CreateVirtualCardCheckoutData
import com.getcatch.android.models.checkout.Price
import com.getcatch.sample.Constants
import java.util.*


val address = Address(
    name = "Test Name",
    address1 = "1234 Main St.",
    address2 = null,
    city = "San Francisco",
    countryCode = "1",
    zoneCode = "CA",
    postalCode = "94105",
    area = null,
    phoneNumber = null,
)

val cartItems = listOf(
    Item(
        imageUrl = "https://cdn.shopify.com/s/files/1/0551/3950/0183/products/20854938-1-pink.jpg?v=1615183168",
        name = "Padded Parka",
        price = Price(amount = 7499, currency = "USD"),
        quantity = 3,
        sku = "1234"
    ),
    Item(
        imageUrl = "https://cdn.shopify.com/s/files/1/0551/3950/0183/products/cart_item_2.png?v=1615183167",
        name = "Classic Mask - Grey Pinstripe",
        price = Price(amount = 1499, currency = "USD"),
        quantity = 2,
        sku = "4567"
    ),
    Item(
        imageUrl = "https://dev.assets.getcatch.com/merchant-assets/examples/cart_item_3.png",
        name = "The Waverly - Cloud",
        price = Price(amount = 9899, currency = "USD"),
        quantity = 1,
        sku = "8910"
    )
)

fun createRandomCheckoutData(merchantOrderId: String): CreateVirtualCardCheckoutData =
    CreateVirtualCardCheckoutData(
        merchantOrderId = merchantOrderId,
        merchantPublicKey = Constants.CATCH_PUBLIC_KEY,
        amounts = Amounts(
            total = 19789,
            subtotal = 18897,
            tax = 585,
            shipping = 307,
            discountTotal = null,
            currency = "USD"
        ),
        billing = address,
        shipping = address,
        shippingMethod = "Ground",
        userCohorts = listOf("test_cohort"),
        items = cartItems,
        merchantUserId = UUID.randomUUID().toString(),
    )

