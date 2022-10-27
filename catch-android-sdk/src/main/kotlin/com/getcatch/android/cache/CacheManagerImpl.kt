package com.getcatch.android.cache

import android.content.Context
import com.getcatch.android.R
import com.getcatch.android.domain.Merchant

internal class CacheManagerImpl(val context: Context) : CacheManager {
    private val merchantPrefs = context.getSharedPreferences(
        context.getString(R.string.merchant_preference_file_key), Context.MODE_PRIVATE
    )

    override var merchant: Merchant?
        get() {
            return Merchant(
                id = merchantPrefs.getString(PREFS_KEY_MERCHANT_ID, null) ?: return null,
                name = merchantPrefs.getString(PREFS_KEY_MERCHANT_NAME, null) ?: return null,
                rewardsRatePercent = merchantPrefs.getFloat(
                    PREFS_KEY_MERCHANT_REWARDS_RATE_PERCENT,
                    DEFAULT_REWARDS_RATE_PERCENT
                ),
                url = merchantPrefs.getString(PREFS_KEY_MERCHANT_URL, null) ?: return null,
                rewardsLifetimeInDays = merchantPrefs.getInt(
                    PREFS_KEY_MERCHANT_REWARDS_LIFETIME_IN_DAYS,
                    DEFAULT_REWARDS_LIFETIME_IN_DAYS
                ),
                cardBackground = merchantPrefs.getString(PREFS_KEY_MERCHANT_CARD_BACKGROUND, null)
                    ?: return null,
                cardFontColor = merchantPrefs.getString(PREFS_KEY_MERCHANT_CARD_FONT_COLOR, null)
                    ?: return null,
                hasTheme = merchantPrefs.getBoolean(PREFS_KEY_MERCHANT_HAS_THEME, false),
            )
        }
        set(value) {
            if (value == null) {
                merchantPrefs.edit().clear().apply()
            } else {
                val editor = merchantPrefs.edit()
                editor.putString(PREFS_KEY_MERCHANT_ID, value.id)
                editor.putString(PREFS_KEY_MERCHANT_NAME, value.name)
                editor.putFloat(PREFS_KEY_MERCHANT_REWARDS_RATE_PERCENT, value.rewardsRatePercent)
                editor.putString(PREFS_KEY_MERCHANT_URL, value.url)
                editor.putInt(
                    PREFS_KEY_MERCHANT_REWARDS_LIFETIME_IN_DAYS,
                    value.rewardsLifetimeInDays
                )
                editor.putString(PREFS_KEY_MERCHANT_CARD_BACKGROUND, value.cardBackground)
                editor.putString(PREFS_KEY_MERCHANT_CARD_FONT_COLOR, value.cardFontColor)
                editor.apply()
            }
        }

    companion object {
        const val DEFAULT_REWARDS_LIFETIME_IN_DAYS = 30 // ~1 month
        const val DEFAULT_REWARDS_RATE_PERCENT = 0.1f // 10 percent

        // Merchant prefs keys
        const val PREFS_KEY_MERCHANT_ID = "PREFS_KEY_MERCHANT_ID"
        const val PREFS_KEY_MERCHANT_NAME = "PREFS_KEY_MERCHANT_NAME"
        const val PREFS_KEY_MERCHANT_REWARDS_RATE_PERCENT =
            "PREFS_KEY_MERCHANT_REWARDS_RATE_PERCENT"
        const val PREFS_KEY_MERCHANT_URL = "PREFS_KEY_MERCHANT_URL"
        const val PREFS_KEY_MERCHANT_REWARDS_LIFETIME_IN_DAYS =
            "PREFS_KEY_MERCHANT_REWARDS_LIFETIME_IN_DAYS"
        const val PREFS_KEY_MERCHANT_CARD_BACKGROUND = "PREFS_KEY_MERCHANT_CARD_BACKGROUND"
        const val PREFS_KEY_MERCHANT_CARD_FONT_COLOR = "PREFS_KEY_MERCHANT_CARD_FONT_COLOR"
        const val PREFS_KEY_MERCHANT_HAS_THEME = "PREFS_KEY_MERCHANT_HAS_THEME"
    }
}
