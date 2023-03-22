package com.getcatch.android.utils

import android.util.Log
import com.getcatch.android.network.NetworkResponse
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import java.net.UnknownHostException

internal suspend inline fun <reified T> handleNetworkResponse(
    requestBlock: () -> HttpResponse
): NetworkResponse<T> {
    var response: HttpResponse? = null
    try {
        response = requestBlock()
    } catch (ex: UnknownHostException) {
        Log.w(
            "Catch",
            "Network request failed. Check network connection and try again.",
            ex
        )
        return NetworkResponse.Failure(
            message = "Network request failed. Check network connection and try again.",
            ex
        )
    }
    return if (response.status.isSuccess()) {
        try {
            NetworkResponse.Success(response.body())
        } catch (ex: JsonConvertException) {
            Log.e(
                "Catch",
                "Error deserializing response",
                ex
            )
            NetworkResponse.Failure(message = "Error deserializing response", ex)
        } catch (ex: NoTransformationFoundException) {
            Log.e(
                "Catch",
                "Response body did not match expected format",
                ex
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
