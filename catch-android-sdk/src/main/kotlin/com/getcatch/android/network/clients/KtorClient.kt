package com.getcatch.android.network.clients

import com.getcatch.android.serialization.SnakeCaseSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

internal fun httpClientFactory(
    engine: HttpClientEngine
) = HttpClient(engine) {
    defaultRequest {
        contentType(ContentType.Application.Json)
    }
    install(ContentNegotiation) {
        json(SnakeCaseSerializer)
    }
    install(HttpTimeout) {
        requestTimeoutMillis = REQUEST_TIMEOUT_MS
    }
}

internal const val REQUEST_TIMEOUT_MS = 60000L // 60 seconds in millis

internal val KtorClient = httpClientFactory(Android.create())
