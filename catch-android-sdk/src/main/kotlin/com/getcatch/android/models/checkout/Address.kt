package com.getcatch.android.models.checkout

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Contains address details. */
@Serializable
public data class Address(
    /** The name of the contact. */
    val name: String,

    /** The street address. */
    @SerialName("address_1")
    val address1: String,

    /** The optional apartment, suite, unit, etc. */
    @SerialName("address_2")
    val address2: String?,

    /** The city. */
    val city: String,

    /** For international addresses where needed, such as name of the suburb for NZ or village for UK. */
    val area: String?,

    /** The state or province abbreviation, such as 'NY' or 'CA'. */
    val zoneCode: String,

    /** The country code. */
    val countryCode: String,

    /** The postal code. */
    val postalCode: String,

    /** The phone number associated with the order's contact. Format: "+12223334444" */
    val phoneNumber: String?,
)
