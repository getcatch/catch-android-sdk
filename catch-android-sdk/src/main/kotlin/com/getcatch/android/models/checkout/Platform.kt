package com.getcatch.android.models.checkout

import android.os.Build
import kotlinx.serialization.Serializable

/** Contains the name and version of the platform in use. */
@Serializable
internal data class Platform(

    /** Name of the platform the merchant is on. */
    val platformType: String = "Android",

    /** Version of the platform the merchant is on. */
    val platformVersion: String =
        Build.VERSION.SDK_INT.toString() + " (" + Build.VERSION.RELEASE + ")"
)
