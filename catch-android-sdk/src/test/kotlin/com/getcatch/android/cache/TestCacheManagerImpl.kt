package com.getcatch.android.cache

import android.content.Context
import com.getcatch.android.test.helpers.FakeDataProvider
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import com.google.common.truth.Truth.assertThat
import org.junit.Test

public class TestCacheManagerImpl {
    private val sharedPrefsMockBuilder: SPMockBuilder = SPMockBuilder()

    @Test
    public fun `merchant, set and get work`() {
        val cacheManager = CacheManagerImpl(sharedPrefsMockBuilder.createContext())
        assertThat(cacheManager.merchant).isNull()
        cacheManager.merchant = FakeDataProvider.merchant
        assertThat(cacheManager.merchant).isEqualTo(FakeDataProvider.merchant)
    }

    /**
     * In this test, we make sure that we don't error if we try deserializing
     * a malformed Merchant JSON object. This should prevent us from having
     * migration issues.
     */
    @Test
    public fun `merchant, malformed cached object returns null and resets`() {
        val mockContext = sharedPrefsMockBuilder.createContext()

        // Grab the shared preferences that the cache manager will use to do some direct edits
        val mockSharedPrefs =
            mockContext.getSharedPreferences(CacheManagerImpl.PREFS_FILE_NAME, Context.MODE_PRIVATE)

        // Set the merchant key to a string that will not deserialize into a Merchant object
        mockSharedPrefs.edit().putString(CacheManagerImpl.PREFS_KEY_MERCHANT, "not json").apply()
        assertThat(
            mockSharedPrefs.getString(
                CacheManagerImpl.PREFS_KEY_MERCHANT,
                null
            )
        ).isEqualTo("not json")

        // Create a cache manager with the same context so it uses the prefs that we edited
        val cacheManager = CacheManagerImpl(mockContext)

        // Make sure that we don't get an error, just a null when deserialization fails
        assertThat(cacheManager.merchant).isNull()

        // Make sure the error handling clears out the shared pref
        assertThat(mockSharedPrefs.getString(CacheManagerImpl.PREFS_KEY_MERCHANT, null)).isNull()
    }

    @Test
    public fun `deviceToken, get and set`() {
        val cacheManager = CacheManagerImpl(sharedPrefsMockBuilder.createContext())
        val testDeviceToken = "TEST_DEVICE_TOKEN"
        assertThat(cacheManager.deviceToken).isNull()
        cacheManager.deviceToken = testDeviceToken
        assertThat(cacheManager.deviceToken).isEqualTo(testDeviceToken)
        cacheManager.deviceToken = null
        assertThat(cacheManager.deviceToken).isNull()
    }
}
