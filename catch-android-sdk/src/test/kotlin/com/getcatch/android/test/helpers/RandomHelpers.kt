package com.getcatch.android.test.helpers

// Descriptive alphabet using three CharRange objects, concatenated
private val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')


internal fun randomString(length: Int = 6): String =
    List(length) { alphabet.random() }.joinToString("")
