package com.getcatch.android.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

internal fun Instant.toDate(timeZone: TimeZone = TimeZone.currentSystemDefault()) =
    this.toLocalDateTime(timeZone).date

internal fun LocalDate.format(
    format: String
): String = DateTimeFormatter.ofPattern(format).format(this.toJavaLocalDate())
