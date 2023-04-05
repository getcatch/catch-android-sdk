package com.getcatch.android.ui.theming.color

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Suppress("LongParameterList")
internal class CatchComposeColors(
    foreground: Color,
    background: Color,
    accent: Color,
    secondaryAccent: Color,
    border: Color,
    buttonText: Color,
) {
    var foreground by mutableStateOf(foreground)
        private set
    var background by mutableStateOf(background)
        private set
    var accent by mutableStateOf(accent)
        private set
    var secondaryAccent by mutableStateOf(secondaryAccent)
        private set
    var border by mutableStateOf(border)
        private set
    var buttonText by mutableStateOf(buttonText)
        private set

    fun copy(
        foreground: Color = this.foreground,
        background: Color = this.background,
        accent: Color = this.accent,
        secondaryAccent: Color = this.secondaryAccent,
        border: Color = this.border,
        buttonText: Color = this.buttonText,
    ): CatchComposeColors = CatchComposeColors(
        foreground,
        background,
        accent,
        secondaryAccent,
        border,
        buttonText,
    )

    fun updateColorsFrom(other: CatchComposeColors) {
        foreground = other.foreground
        background = other.background
        accent = other.accent
        secondaryAccent = other.secondaryAccent
        border = other.border
        buttonText = other.buttonText
    }
}
