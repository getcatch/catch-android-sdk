package com.getcatch.android.theming

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.getcatch.android.R

internal object CatchTypography {
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

    object CatchTextStyles {
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

        /**
         * Default text style for widgets (14/20, 400)
         */
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
            fontWeight = FontWeight.W500,
        )
        val buttonLabelCompact = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.W500,
        )
    }
}
