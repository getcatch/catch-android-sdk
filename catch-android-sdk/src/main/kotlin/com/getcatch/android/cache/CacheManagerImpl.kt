package com.getcatch.android.cache

import android.content.Context
import android.util.Log
import com.getcatch.android.R
import com.getcatch.android.models.Merchant
import com.getcatch.android.serialization.SnakeCaseSerializer
import io.ktor.utils.io.printStack
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

internal class CacheManagerImpl(val context: Context) : CacheManager {
    private val sharedPrefs = context.getSharedPreferences(
        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
    )

    override var merchant: Merchant?
        get() {
            val merchantJsonString = sharedPrefs.getString(PREFS_KEY_MERCHANT, null) ?: return null
            return try {
                SnakeCaseSerializer.decodeFromString(merchantJsonString)
            } catch (ex: SerializationException) {
                ex.printStack()
                merchant = null
                null
            } catch (ex: IllegalArgumentException) {
                ex.printStack()
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

    companion object {
        // Merchant prefs keys
        const val PREFS_KEY_MERCHANT = "PREFS_KEY_MERCHANT"
    }
}
