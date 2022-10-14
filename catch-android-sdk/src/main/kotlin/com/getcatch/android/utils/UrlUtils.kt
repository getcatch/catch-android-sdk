package com.getcatch.android.utils

import io.ktor.http.URLBuilder

/**
 * A utility method for building urls
 *
 * @param path The url path (can be appended to in the block)
 * @param block A function block that allows you to call methods on the internal [URLBuilder]
 * @return The url generated from the path plus any modifications made to via the [block]
 */
internal fun buildUrl(path: String, block: URLBuilder.(URLBuilder) -> Unit): String =
    URLBuilder(urlString = path).let {
        it.block(it)
        it.buildString()
    }
