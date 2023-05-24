package com.getcatch.android.models

import kotlinx.serialization.Serializable

@Serializable
internal data class CatchDeviceTokenPayload(val deviceToken: String?)
