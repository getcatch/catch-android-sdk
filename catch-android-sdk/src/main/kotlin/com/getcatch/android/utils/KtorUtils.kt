package com.getcatch.android.utils

import com.getcatch.android.network.NetworkResponse
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import timber.log.Timber
import java.net.UnknownHostException

internal suspend inline fun <reified T> handleNetworkResponse(
    requestBlock: () -> HttpResponse
): NetworkResponse<T> {
    var response: HttpResponse? = null
    try {
        response = requestBlock()
    } catch (ex: UnknownHostException) {
        Timber.w(
            t = ex,
            message = "Network request failed. Check network connection and try again."
        )
        return NetworkResponse.Failure(
            message = "Network request failed. Check network connection and try again.",
            ex
        )
    } catch (ex: HttpRequestTimeoutException) {
        Timber.w(
            t = ex,
            message = "Network request failed due to timeout.",
        )
        return NetworkResponse.Failure(
            message = "Network request failed due to timeout.",
            ex
        )
    }
    return if (response.status.isSuccess()) {
        try {
            NetworkResponse.Success(response.body())
        } catch (ex: JsonConvertException) {
            Timber.e(
                t = ex,
                message = "Error deserializing response",
            )
            NetworkResponse.Failure(message = "Error deserializing response", ex)
        } catch (ex: NoTransformationFoundException) {
            Timber.e(
                t = ex,
                message = "Response body did not match expected format",
            )
            NetworkResponse.Failure(
                "Response body did not match expected format",
                ex
            )
        }
    } else {
        NetworkResponse.Failure(message = "Request failed with status code ${response.status.value}")
    }
}
