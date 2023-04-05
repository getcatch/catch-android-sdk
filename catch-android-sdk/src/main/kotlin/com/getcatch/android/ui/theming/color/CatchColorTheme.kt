package com.getcatch.android.ui.theming.color

import androidx.compose.ui.graphics.Color

internal sealed class CatchColorTheme {
    abstract val foreground: Long
    abstract val background: Long
    abstract val accent: Long
    abstract val secondaryAccent: Long
    abstract val border: Long
    abstract val buttonText: Long

    fun toComposeColors() = CatchComposeColors(
        foreground = Color(this.foreground),
        background = Color(this.background),
        accent = Color(this.accent),
        secondaryAccent = Color(this.secondaryAccent),
        border = Color(this.border),
        buttonText = Color(this.buttonText),
    )

    object Light : CatchColorTheme() {
        override val foreground = CatchColors.GREY_7
        override val background = CatchColors.WHITE
        override val accent = CatchColors.PINK_2
        override val secondaryAccent = CatchColors.GREEN_2
        override val border = CatchColors.GREY_3
        override val buttonText = CatchColors.WHITE
    }

    object LightMono : CatchColorTheme() {
        override val foreground = CatchColors.GREY_7
        override val background = CatchColors.WHITE
        override val accent = CatchColors.GREY_7
        override val secondaryAccent = CatchColors.GREY_7
        override val border = CatchColors.GREY_3
        override val buttonText = CatchColors.WHITE
    }

    object Dark : CatchColorTheme() {
        override val foreground = CatchColors.WHITE
        override val background = CatchColors.BLACK
        override val accent = CatchColors.DARK_MODE_PINK
        override val secondaryAccent = CatchColors.DARK_MODE_GREEN
        override val border = CatchColors.GREY_4
        override val buttonText = CatchColors.BLACK
    }

    object DarkMono : CatchColorTheme() {
        override val foreground = CatchColors.WHITE
        override val background = CatchColors.BLACK
        override val accent = CatchColors.WHITE
        override val secondaryAccent = CatchColors.WHITE
        override val border = CatchColors.GREY_4
        override val buttonText = CatchColors.BLACK
    }
}
