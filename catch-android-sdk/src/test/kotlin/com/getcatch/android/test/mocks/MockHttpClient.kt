package com.getcatch.android.test.mocks

import com.getcatch.android.network.clients.httpClientFactory
import com.google.common.truth.Truth
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel

public fun createMockHttpClient(
    method: HttpMethod,
    url: String,
    body: String,
    status: HttpStatusCode = HttpStatusCode.OK
): HttpClient {
    val mockEngine = MockEngine { request ->
        Truth.assertThat(request.method).isEqualTo(method)
        Truth.assertThat(request.url.toString()).isEqualTo(url)
        respond(
            content = ByteReadChannel(body),
            status = status,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }
    return httpClientFactory(mockEngine)
}
