package com.getcatch.android.styling

import com.getcatch.android.styling.values.ColorValue
import com.getcatch.android.styling.values.FontWeight
import com.getcatch.android.styling.values.TextTransform

public data class TextStyle(
    val fontSize: Float? = null,
    val fontColor: ColorValue? = null,
    val fontWeight: FontWeight? = null,
    val lineHeight: Float? = null,
    val letterSpacing: Float? = null,
    val textTransform: TextTransform? = null,
)
