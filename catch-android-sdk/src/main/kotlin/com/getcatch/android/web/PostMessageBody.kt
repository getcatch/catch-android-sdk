package com.getcatch.android.web

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

@Serializable
internal data class PostMessageBody(val action: String, val data: JsonObject? = null) {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        private val json: Json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

        fun fromJsonString(jsonString: String): PostMessageBody = json.decodeFromString(jsonString)
    }
}
