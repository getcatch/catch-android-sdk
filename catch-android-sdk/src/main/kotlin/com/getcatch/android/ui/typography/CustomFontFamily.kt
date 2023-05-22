package com.getcatch.android.ui.typography

import androidx.compose.ui.text.font.FontFamily

/**
 * A utility class used to allow for a custom font family to be provided
 * in a way that is both Compose and view based app friendly.
 */
public class CustomFontFamily {
    internal val composeFontFamily: FontFamily
    public constructor(vararg customFonts: CustomFont) {
        composeFontFamily = FontFamily(customFonts.toList().map { it.composeFont })
    }

    public constructor(fontFamily: FontFamily) {
        this.composeFontFamily = fontFamily
    }
}
