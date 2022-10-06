package com.getcatch.android.clients

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// private const val AUTHORIZATION_HEADER = "Authorization"
// private var API_KEY: String = "Your API Key here"

private val client = HttpClient(Android) {
    defaultRequest {
//        header(AUTHORIZATION_HEADER, "BEARER $API_KEY")
        contentType(ContentType.Application.Json)
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
}

internal val KtorClient = client
