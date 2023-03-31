package com.getcatch.android.utils

internal fun String.capitalizeWords(): String =
    split(" ")
        .joinToString(separator = " ") { str ->
            str.replaceFirstChar { char ->
                char.uppercase()
            }
        }
