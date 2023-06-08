package com.getcatch.android.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import io.ktor.http.URLBuilder
import timber.log.Timber

/**
 * A utility method for building urls.
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

/**
 * A utility for extracting the base url from a [Uri] by stripping any params and/or fragments.
 */
internal val Uri.baseUrl: String
    get() = Uri.Builder()
        .scheme(this.scheme)
        .authority(this.authority)
        .path(this.path)
        .build()
        .toString()

internal fun URLBuilder.parameter(key: String, value: Any?): Unit =
    value?.let { parameters.append(key, it.toString()) } ?: Unit

internal fun launchUrlIntent(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ensureUrlPrefix(url)))
        context.startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        Timber.e(t = ex, message = "CatchSDK", "Invalid url: $url")
    }
}

internal fun ensureUrlPrefix(url: String): String =
    if (url.startsWith("https://") || url.startsWith("http://")) url else "https://$url"
