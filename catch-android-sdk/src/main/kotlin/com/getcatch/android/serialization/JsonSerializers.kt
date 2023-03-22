package com.getcatch.android.serialization

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

internal val SnakeCaseSerializer = Json {
    prettyPrint = true
    isLenient = true
    ignoreUnknownKeys = true
    namingStrategy = JsonNamingStrategy.SnakeCase
}
