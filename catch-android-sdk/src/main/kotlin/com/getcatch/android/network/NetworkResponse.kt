package com.getcatch.android.network

internal sealed interface NetworkResponse<T> {
    class Success<T>(val body: T) : NetworkResponse<T>
    class Failure<T>(val message: String, val error: Exception? = null) : NetworkResponse<T>
}
