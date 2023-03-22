package com.getcatch.android.repository

import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.test.helpers.FakeDataProvider
import com.getcatch.android.test.mocks.MockCacheManager
import com.getcatch.android.test.mocks.MockMerchantsSvcClient
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

public class TestMerchantRepositoryImpl {

    @Test
    public fun `activeMerchant initializes with cached value`() {
        val mockCache = MockCacheManager(merchant = null)
        val mockMerchantsSvcClient = MockMerchantsSvcClient()
        val publicKey = PublicKey("TEST_MERCHANT_PUBLIC_KEY")

        MerchantRepositoryImpl(
            merchantsSvcClient = mockMerchantsSvcClient,
            cache = mockCache,
            publicKey = publicKey,
        ).also {
            assertThat(it.activeMerchant.value).isNull()
        }

        mockCache.merchant = FakeDataProvider.merchant
        MerchantRepositoryImpl(
            merchantsSvcClient = mockMerchantsSvcClient,
            cache = mockCache,
            publicKey = publicKey,
        ).also {
            assertThat(it.activeMerchant.value).isEqualTo(FakeDataProvider.merchant)
        }
    }

    @Test
    public fun `loadMerchant, network success updates activeMerchant and cache`() {
        val mockCache = MockCacheManager(merchant = null)
        val publicKey = PublicKey("TEST_MERCHANT_PUBLIC_KEY")
        val mockMerchantsSvcClient =
            MockMerchantsSvcClient(
                mapOf(
                    publicKey to NetworkResponse.Success(FakeDataProvider.merchant)
                )
            )

        val merchantRepo = MerchantRepositoryImpl(
            merchantsSvcClient = mockMerchantsSvcClient,
            cache = mockCache,
            publicKey = publicKey,
        )

        assertThat(merchantRepo.activeMerchant.value).isNull()
        runBlocking {
            merchantRepo.loadMerchant()
        }
        assertThat(merchantRepo.activeMerchant.value).isEqualTo(FakeDataProvider.merchant)
        assertThat(mockCache.merchant).isEqualTo(FakeDataProvider.merchant)
    }

    @Test
    public fun `loadMerchant, network failure does not invalidate activeMerchant and cache`() {
        val mockCache = MockCacheManager(merchant = FakeDataProvider.merchant)
        val publicKey = PublicKey("TEST_MERCHANT_PUBLIC_KEY")
        val mockMerchantsSvcClient =
            MockMerchantsSvcClient(
                mapOf(
                    publicKey to NetworkResponse.Failure("Test failure")
                )
            )

        val merchantRepo = MerchantRepositoryImpl(
            merchantsSvcClient = mockMerchantsSvcClient,
            cache = mockCache,
            publicKey = publicKey,
        )

        assertThat(merchantRepo.activeMerchant.value).isEqualTo(FakeDataProvider.merchant)
        assertThat(mockCache.merchant).isEqualTo(FakeDataProvider.merchant)
        runBlocking {
            merchantRepo.loadMerchant()
        }
        assertThat(merchantRepo.activeMerchant.value).isEqualTo(FakeDataProvider.merchant)
        assertThat(mockCache.merchant).isEqualTo(FakeDataProvider.merchant)
    }
}
