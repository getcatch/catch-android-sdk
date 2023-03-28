package com.getcatch.android.network.clients

import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.merchants.MerchantsSvcClientImpl
import com.getcatch.android.test.helpers.ResourceHelpers
import com.getcatch.android.test.mocks.MockHttpClient
import com.google.common.truth.Truth.assertThat
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.fail

public class TestMerchantsSvcClientImpl {
    private val testPublicKey = PublicKey("TEST_MERCHANT_PUBLIC_KEY")
    @Test
    public fun `loadPublicMerchantData, success`() {
        val responseJson = ResourceHelpers.loadResource("load-public-merchant-response.json")
        val mockClient = MockHttpClient()
        val merchantsSvcClient = MerchantsSvcClientImpl(
            mockClient.client,
            Environment.PRODUCTION,
        )

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${merchantsSvcClient.baseUrl}/merchants/public_keys/${testPublicKey.value}/public",
            responseBody = responseJson
        )

        runBlocking {
            val response = merchantsSvcClient.loadPublicMerchantData(testPublicKey)
            assertThat(response).isInstanceOf(NetworkResponse.Success::class.java)
            when (response) {
                is NetworkResponse.Success -> assertThat(response.body.id).isEqualTo("humans-s768ng")
                is NetworkResponse.Failure -> fail("Request with valid public key should succeed and return merchant")
            }
        }
    }

    @Test
    public fun `loadPublicMerchantData, malformed response body`() {
        val responseJson = "{\"test\":100}"
        val mockClient = MockHttpClient()
        val merchantsSvcClient = MerchantsSvcClientImpl(
            mockClient.client,
            Environment.PRODUCTION,
        )

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${merchantsSvcClient.baseUrl}/merchants/public_keys/${testPublicKey.value}/public",
            responseBody = responseJson
        )

        runBlocking {
            val response = merchantsSvcClient.loadPublicMerchantData(testPublicKey)
            assertThat(response).isInstanceOf(NetworkResponse.Failure::class.java)
            when (response) {
                is NetworkResponse.Success -> fail("Response with improper body should fail JSON conversion")
                is NetworkResponse.Failure -> assertThat(response.error).isInstanceOf(JsonConvertException::class.java)
            }
        }
    }

    @Test
    public fun `loadPublicMerchantData, non 200 status code`() {
        val responseJson = "{\"message\":\"Something went wrong\"}"
        val mockClient = MockHttpClient()
        val merchantsSvcClient = MerchantsSvcClientImpl(
            mockClient.client,
            Environment.PRODUCTION,
        )

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${merchantsSvcClient.baseUrl}/merchants/public_keys/${testPublicKey.value}/public",
            responseBody = responseJson,
            status = HttpStatusCode.NotFound
        )

        runBlocking {
            val response = merchantsSvcClient.loadPublicMerchantData(testPublicKey)
            assertThat(response).isInstanceOf(NetworkResponse.Failure::class.java)
            when (response) {
                is NetworkResponse.Success -> fail("Response with non 200 status code should return a Failure")
                is NetworkResponse.Failure -> assertThat(response.error).isNull()
            }
        }
    }
}
