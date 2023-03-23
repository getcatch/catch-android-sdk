package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DonationRecipient(val name: String, val url: String)
