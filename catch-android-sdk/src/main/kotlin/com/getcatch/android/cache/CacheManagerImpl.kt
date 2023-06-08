package com.getcatch.android.cache

import android.content.Context
import com.getcatch.android.models.Merchant
import com.getcatch.android.serialization.SnakeCaseSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import timber.log.Timber

internal class CacheManagerImpl(val context: Context) : CacheManager {
    private val sharedPrefs = context.getSharedPreferences(
        PREFS_FILE_NAME,
        Context.MODE_PRIVATE,
    )

    override var merchant: Merchant?
        get() {
            val merchantJsonString = sharedPrefs.getString(PREFS_KEY_MERCHANT, null) ?: return null
            return try {
                SnakeCaseSerializer.decodeFromString(merchantJsonString)
            } catch (ex: SerializationException) {
                Timber.w(
                    ex,
                    "Error deserializing cached merchant.",
                )
                merchant = null
                null
            } catch (ex: IllegalArgumentException) {
                Timber.w(
                    ex,
                    "Error deserializing cached merchant.",
                )
                merchant = null
                null
            }
        }
        set(value) {
            if (value == null) {
                sharedPrefs.edit()
                    .putString(PREFS_KEY_MERCHANT, null)
                    .apply()
            } else {
                sharedPrefs.edit()
                    .putString(PREFS_KEY_MERCHANT, SnakeCaseSerializer.encodeToString(value))
                    .apply()
            }
        }

    override var deviceToken: String?
        get() = sharedPrefs.getString(PREFS_KEY_DEVICE_TOKEN, null)
        set(value) {
            sharedPrefs.edit().putString(PREFS_KEY_DEVICE_TOKEN, value).apply()
        }

    companion object {
        const val PREFS_FILE_NAME = "PREFS_FILE_NAME_CATCH_SDK_SHARED_PREFERENCES"
        const val PREFS_KEY_MERCHANT = "PREFS_KEY_MERCHANT"
        const val PREFS_KEY_DEVICE_TOKEN = "PREFS_KEY_DEVICE_TOKEN"
    }
}
