package com.getcatch.android.ui.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

internal object CatchTextStyles {
    val h1 = TextStyle(
        fontSize = 26.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.W700,
    )

    val h2 = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.W700,
    )

    val h3 = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W700,
    )

    val h4 = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W700,
    )

    val bodyLarge = TextStyle(
        fontSize = 18.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.W400,
    )

    val bodyRegular = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W400,
    )

    val bodySmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W400,
    )

    val linkLarge = TextStyle(
        fontSize = 18.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.W400,
        textDecoration = TextDecoration.Underline,
    )

    val linkRegular = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W500,
        textDecoration = TextDecoration.Underline,
    )

    val linkSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W500,
        textDecoration = TextDecoration.Underline,
    )

    val buttonLabel = TextStyle(
        fontSize = 18.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.W700,
    )

    val buttonLabelCompact = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W500,
    )
}
