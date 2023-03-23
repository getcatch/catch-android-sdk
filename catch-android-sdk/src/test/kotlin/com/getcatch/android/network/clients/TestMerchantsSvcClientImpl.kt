package com.getcatch.android.network.clients

import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.merchants.MerchantsSvcClientImpl
import com.getcatch.android.test.helpers.ResourceHelpers
import com.getcatch.android.test.mocks.createMockHttpClient
import com.google.common.truth.Truth.assertThat
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.fail

public class TestMerchantsSvcClientImpl {
    @Test
    public fun `loadPublicMerchantData, success`() {
        val responseJson = ResourceHelpers.loadResource("load-public-merchant-response.json")
        val testPublicKey = "TEST_MERCHANT_PUBLIC_KEY"
        val httpClient = createMockHttpClient(
            method = HttpMethod.Get,
            url = "https://dev.app.getcatch.com/api/merchants-svc/merchants/public_keys/$testPublicKey/public",
            body = responseJson
        )
        val merchantsSvcClient = MerchantsSvcClientImpl(
            httpClient,
            Environment.PRODUCTION,
        )
        runBlocking {
            val response = merchantsSvcClient.loadPublicMerchantData(PublicKey(testPublicKey))
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
        val testPublicKey = "TEST_MERCHANT_PUBLIC_KEY"
        val httpClient = createMockHttpClient(
            method = HttpMethod.Get,
            url = "https://dev.app.getcatch.com/api/merchants-svc/merchants/public_keys/$testPublicKey/public",
            body = responseJson
        )
        val merchantsSvcClient = MerchantsSvcClientImpl(
            httpClient,
            Environment.PRODUCTION,
        )
        runBlocking {
            val response = merchantsSvcClient.loadPublicMerchantData(PublicKey(testPublicKey))
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
        val testPublicKey = "TEST_MERCHANT_PUBLIC_KEY"
        val httpClient = createMockHttpClient(
            method = HttpMethod.Get,
            url = "https://dev.app.getcatch.com/api/merchants-svc/merchants/public_keys/$testPublicKey/public",
            body = responseJson,
            status = HttpStatusCode.BadRequest
        )
        val merchantsSvcClient = MerchantsSvcClientImpl(
            httpClient,
            Environment.PRODUCTION,
        )
        runBlocking {
            val response = merchantsSvcClient.loadPublicMerchantData(PublicKey(testPublicKey))
            assertThat(response).isInstanceOf(NetworkResponse.Failure::class.java)
            when (response) {
                is NetworkResponse.Success -> fail("Response with non 200 status code should return a Failure")
                is NetworkResponse.Failure -> assertThat(response.error).isNull()
            }
        }
    }
}
