package com.getcatch.android.models.checkout

import android.os.Build
import kotlinx.serialization.Serializable

/** Contains the name and version of the platform in use. */
@Serializable
internal object Platform {

    /** Name of the platform the merchant is on. */
    const val platformType: String = "Android"

    /** Version of the platform the merchant is on. */
    val platformVersion: String =
        Build.VERSION.SDK_INT.toString() + " (" + Build.VERSION.RELEASE + ")"
}
