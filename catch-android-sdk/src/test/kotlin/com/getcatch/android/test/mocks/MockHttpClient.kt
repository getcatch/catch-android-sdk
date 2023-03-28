package com.getcatch.android.test.mocks

import com.getcatch.android.network.clients.httpClientFactory
import com.getcatch.android.test.exceptions.RequestNotMockedException
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel

internal class MockHttpClient {

    private val mockReqToResRegistry: MutableMap<MockRequest, MockResponse> = mutableMapOf()

    private val mockEngine = MockEngine { request ->
        val receivedRequest = MockRequest(method = request.method, url = request.url.toString())
        val mockedResponse = mockReqToResRegistry[receivedRequest]
            ?: throw RequestNotMockedException.create(receivedRequest, mockReqToResRegistry.keys)

        respond(
            content = ByteReadChannel(mockedResponse.body),
            status = mockedResponse.status,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    val client = httpClientFactory(mockEngine)

    fun addResponse(
        method: HttpMethod,
        url: String,
        responseBody: String,
        status: HttpStatusCode = HttpStatusCode.OK
    ) {
        val mockRequest = MockRequest(method = method, url = url)
        val mockResponse = MockResponse(body = responseBody, status = status)
        mockReqToResRegistry[mockRequest] = mockResponse
    }

    fun reset() {
        mockReqToResRegistry.clear()
    }
}
