package com.getcatch.android.ui.typography

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import com.getcatch.android.ui.styles.values.FontWeight

/**
 * A utility class for view based apps to provide custom fonts. Meant to be used
 * to construct an instance of [CustomFontFamily] which can be set globally via the
 * [CatchOptions] parameter in [Catch.initialize] or via the [Catch.setCustomFontFamily]
 * method.
 */
public class CustomFont(
    resId: Int,
    weight: FontWeight,
    isItalic: Boolean = false,
) {
    internal val composeFont: Font

    init {
        val fontStyle = if (isItalic) FontStyle.Italic else FontStyle.Normal
        composeFont = Font(resId, weight.toComposeFontWeight(), fontStyle)
    }
}
