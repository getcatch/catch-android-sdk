package com.getcatch.android.repository

import com.getcatch.android.models.PublicUserData
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import com.getcatch.android.test.helpers.FakeDataProvider
import com.getcatch.android.test.mocks.MockCacheManager
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyBlocking

public class TestUserRepositoryImpl {
    private val testDeviceToken = "TEST_DEVICE_TOKEN"
    private val testMerchantId = "TEST_MERCHANT_ID"

    @Test
    public fun `deviceToken, doesn't update to blank strings`() {
        val mockCacheManager = MockCacheManager()
        val mockedTxnClient = mock(TransactionsSvcClient::class.java)
        val userRepo = UserRepositoryImpl(mockedTxnClient, mockCacheManager)

        assertThat(mockCacheManager.deviceToken).isNull()
        assertThat(userRepo.deviceToken.value).isNull()


        userRepo.updateDeviceToken(testDeviceToken)
        assertThat(userRepo.deviceToken.value).isEqualTo(testDeviceToken)
        assertThat(mockCacheManager.deviceToken).isEqualTo(testDeviceToken)

        val blankString = " "
        userRepo.updateDeviceToken(blankString)
        assertThat(userRepo.deviceToken.value).isEqualTo(testDeviceToken)
        assertThat(mockCacheManager.deviceToken).isEqualTo(testDeviceToken)
    }

    @Test
    public fun `loadUserData, network success updates activeUser and didFetchUserData`() {
        val mockCacheManager = MockCacheManager(deviceToken = testDeviceToken)
        val mockedTxnClient = mock<TransactionsSvcClient> {
            onBlocking {
                fetchUserData(
                    testDeviceToken,
                    testMerchantId
                )
            } doReturn NetworkResponse.Success(FakeDataProvider.user)
        }
        val userRepo = UserRepositoryImpl(mockedTxnClient, mockCacheManager)
        assertThat(userRepo.activeUser.value).isNull()
        assertThat(userRepo.didFetchUserData.value).isFalse()
        runBlocking {
            userRepo.loadUserData(testMerchantId)
        }
        verifyBlocking(mockedTxnClient) { fetchUserData(testDeviceToken, testMerchantId) }
        assertThat(userRepo.activeUser.value).isEqualTo(FakeDataProvider.user)
        assertThat(userRepo.didFetchUserData.value).isTrue()
    }

    @Test
    public fun `loadUserData, network failure updates activeUser and didFetchUserData`() {
        val mockCacheManager = MockCacheManager(deviceToken = testDeviceToken)
        val mockedTxnClient = mock<TransactionsSvcClient> {
            onBlocking {
                fetchUserData(
                    testDeviceToken,
                    testMerchantId
                )
            } doReturn NetworkResponse.Failure("Something went wrong")
        }
        val userRepo = UserRepositoryImpl(mockedTxnClient, mockCacheManager)
        assertThat(userRepo.activeUser.value).isNull()
        assertThat(userRepo.didFetchUserData.value).isFalse()
        runBlocking {
            userRepo.loadUserData(testMerchantId)
        }
        verifyBlocking(mockedTxnClient) { fetchUserData(testDeviceToken, testMerchantId) }
        assertThat(userRepo.activeUser.value).isEqualTo(PublicUserData.noData)
        assertThat(userRepo.didFetchUserData.value).isTrue()
    }

    @Test
    public fun `loadUserData, null deviceToken updates activeUser and didFetchUserData`() {
        val mockCacheManager = MockCacheManager(deviceToken = null)
        val mockedTxnClient = mock<TransactionsSvcClient>()
        val userRepo = UserRepositoryImpl(mockedTxnClient, mockCacheManager)
        assertThat(userRepo.activeUser.value).isNull()
        assertThat(userRepo.didFetchUserData.value).isFalse()
        runBlocking {
            userRepo.loadUserData(testMerchantId)
        }
        assertThat(userRepo.activeUser.value).isEqualTo(PublicUserData.noData)
        assertThat(userRepo.didFetchUserData.value).isTrue()
    }
}
