package com.getcatch.android.test.mocks

import io.ktor.http.HttpStatusCode

internal data class MockResponse(
    val body: String,
    val status: HttpStatusCode,
)
