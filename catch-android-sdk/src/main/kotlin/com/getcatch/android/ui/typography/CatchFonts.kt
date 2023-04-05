package com.getcatch.android.ui.typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.getcatch.android.R

internal object CatchFonts {
    val circularFontFamily: FontFamily = FontFamily(
        Font(
            resId = R.font.circular_thin,
            weight = FontWeight.W100,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_thin_italic,
            weight = FontWeight.W100,
            style = FontStyle.Italic,
        ),
        Font(
            resId = R.font.circular_light,
            weight = FontWeight.W200,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_light_italic,
            weight = FontWeight.W200,
            style = FontStyle.Italic,
        ),
        Font(
            resId = R.font.circular_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_italic,
            weight = FontWeight.W400,
            style = FontStyle.Italic,
        ),
        Font(
            resId = R.font.circular_book,
            weight = FontWeight.W500,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_book_italic,
            weight = FontWeight.W500,
            style = FontStyle.Italic,
        ),
        Font(
            resId = R.font.circular_medium,
            weight = FontWeight.W600,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_medium_italic,
            weight = FontWeight.W600,
            style = FontStyle.Italic,
        ),
        Font(
            resId = R.font.circular_bold,
            weight = FontWeight.W700,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_bold_italic,
            weight = FontWeight.W700,
            style = FontStyle.Italic,
        ),
        Font(
            resId = R.font.circular_black,
            weight = FontWeight.W800,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_black_italic,
            weight = FontWeight.W800,
            style = FontStyle.Italic,
        ),
        Font(
            resId = R.font.circular_extra_black,
            weight = FontWeight.W900,
            style = FontStyle.Normal,
        ),
        Font(
            resId = R.font.circular_extra_black_italic,
            weight = FontWeight.W900,
            style = FontStyle.Italic,
        ),
    )
}
