package com.getcatch.android.test.mocks

import io.ktor.http.HttpMethod

internal data class MockRequest(
    val method: HttpMethod,
    val url: String,
) {
    override fun toString() = "${method.value} $url"
}
