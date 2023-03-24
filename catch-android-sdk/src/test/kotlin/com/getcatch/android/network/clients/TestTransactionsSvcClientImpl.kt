package com.getcatch.android.network.clients

import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClientImpl
import com.getcatch.android.test.helpers.ResourceHelpers
import com.getcatch.android.test.mocks.MockHttpClient
import com.google.common.truth.Truth.assertThat
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.fail

public class TestTransactionsSvcClientImpl {
    private val testDeviceId = "TEST_DEVICE_ID"
    private val testMerchantId = "TEST_MERCHANT_ID"

    @Test
    public fun `fetchUserData, success`() {
        val responseJson = ResourceHelpers.loadResource("get-public-user-data-response.json")
        val mockClient = MockHttpClient()
        val transactionsSvcClient = TransactionsSvcClientImpl(
            mockClient.client,
            Environment.PRODUCTION,
        )

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${transactionsSvcClient.baseUrl}/user_devices/$testDeviceId/user_data?merchant_id=$testMerchantId",
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchUserData(testDeviceId, testMerchantId)
            when (response) {
                is NetworkResponse.Success -> assertThat(response.body.userFirstName)
                    .isEqualTo("Luigi")
                is NetworkResponse.Failure -> fail(
                    "Request with valid device and merchant id should succeed and return user data"
                )
            }
        }
    }

    @Test
    public fun `fetchUserData, malformed response body`() {
        val responseJson = "not json"
        val mockClient = MockHttpClient()
        val transactionsSvcClient = TransactionsSvcClientImpl(
            mockClient.client,
            Environment.PRODUCTION,
        )

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${transactionsSvcClient.baseUrl}/user_devices/$testDeviceId/user_data?merchant_id=$testMerchantId",
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchUserData(testDeviceId, testMerchantId)
            when (response) {
                is NetworkResponse.Success -> fail("Response with improper body should fail JSON conversion")
                is NetworkResponse.Failure -> assertThat(response.error).isInstanceOf(JsonConvertException::class.java)
            }
        }
    }

    @Test
    public fun `fetchUserData, non 200 status code`() {
        val responseJson = "{\"message\":\"Something went wrong\"}"
        val mockClient = MockHttpClient()
        val transactionsSvcClient = TransactionsSvcClientImpl(
            mockClient.client,
            Environment.PRODUCTION,
        )

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${transactionsSvcClient.baseUrl}/user_devices/$testDeviceId/user_data?merchant_id=$testMerchantId",
            responseBody = responseJson,
            status = HttpStatusCode.BadRequest,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchUserData(testDeviceId, testMerchantId)
            when (response) {
                is NetworkResponse.Success -> fail("Response with non 200 status code should return a Failure")
                is NetworkResponse.Failure -> assertThat(response.error).isNull()
            }
        }
    }
}
