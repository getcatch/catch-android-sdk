package com.getcatch.android.theming

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

internal object CatchColorThemes {
    val Light by lazy { lightColors() }
    val LightMono by lazy { lightColors(mono = true) }
    val Dark by lazy { darkColors() }
    val DarkMono by lazy { darkColors(mono = true) }
}

@Suppress("LongParameterList")
internal class CatchColors(
    textPrimary: Color,
    textSecondary: Color,
    textTertiary: Color,
    textDisabled: Color,
    textButton: Color,
    bgPrimary: Color,
    bgSecondary: Color,
    bgTertiary: Color,
    funTextEarning: Color,
    funTextRedeeming: Color,
    funTextAlert: Color,
    funTextError: Color,
    funBgRedeeming: Color,
    funBgAlert: Color,
    funBgError: Color,
    funBgDonating: Color,
    funBgMarketing: Color,
    borderDefault: Color,
    borderSecondaryButton: Color,
    borderSelected: Color,
    borderError: Color,
    borderDonating: Color,
    buttonPrimary: Color,
    buttonSecure: Color,
    buttonSecondary: Color,
) {
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textTertiary by mutableStateOf(textTertiary)
        private set
    var textDisabled by mutableStateOf(textDisabled)
        private set
    var textButton by mutableStateOf(textButton)
        private set
    var bgPrimary by mutableStateOf(bgPrimary)
        private set
    var bgSecondary by mutableStateOf(bgSecondary)
        private set
    var bgTertiary by mutableStateOf(bgTertiary)
        private set
    var funTextEarning by mutableStateOf(funTextEarning)
        private set
    var funTextRedeeming by mutableStateOf(funTextRedeeming)
        private set
    var funTextAlert by mutableStateOf(funTextAlert)
        private set
    var funTextError by mutableStateOf(funTextError)
        private set
    var funBgRedeeming by mutableStateOf(funBgRedeeming)
        private set
    var funBgAlert by mutableStateOf(funBgAlert)
        private set
    var funBgError by mutableStateOf(funBgError)
        private set
    var funBgDonating by mutableStateOf(funBgDonating)
        private set
    var funBgMarketing by mutableStateOf(funBgMarketing)
        private set
    var borderDefault by mutableStateOf(borderDefault)
        private set
    var borderSecondaryButton by mutableStateOf(borderSecondaryButton)
        private set
    var borderSelected by mutableStateOf(borderSelected)
        private set
    var borderError by mutableStateOf(borderError)
        private set
    var borderDonating by mutableStateOf(borderDonating)
        private set
    var buttonPrimary by mutableStateOf(buttonPrimary)
        private set
    var buttonSecure by mutableStateOf(buttonSecure)
        private set
    var buttonSecondary by mutableStateOf(buttonSecondary)
        private set

    fun copy(
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        textTertiary: Color = this.textTertiary,
        textDisabled: Color = this.textDisabled,
        textButton: Color = this.textButton,
        bgPrimary: Color = this.bgPrimary,
        bgSecondary: Color = this.bgSecondary,
        bgTertiary: Color = this.bgTertiary,
        funTextEarning: Color = this.funTextEarning,
        funTextRedeeming: Color = this.funTextRedeeming,
        funTextAlert: Color = this.funTextAlert,
        funTextError: Color = this.funTextError,
        funBgRedeeming: Color = this.funBgRedeeming,
        funBgAlert: Color = this.funBgAlert,
        funBgError: Color = this.funBgError,
        funBgDonating: Color = this.funBgDonating,
        funBgMarketing: Color = this.funBgMarketing,
        borderDefault: Color = this.borderDefault,
        borderSecondaryButton: Color = this.borderSecondaryButton,
        borderSelected: Color = this.borderSelected,
        borderError: Color = this.borderError,
        borderDonating: Color = this.borderDonating,
        buttonPrimary: Color = this.buttonPrimary,
        buttonSecure: Color = this.buttonSecure,
        buttonSecondary: Color = this.buttonSecondary,
    ): CatchColors = CatchColors(
        textPrimary,
        textSecondary,
        textTertiary,
        textDisabled,
        textButton,
        bgPrimary,
        bgSecondary,
        bgTertiary,
        funTextEarning,
        funTextRedeeming,
        funTextAlert,
        funTextError,
        funBgRedeeming,
        funBgAlert,
        funBgError,
        funBgDonating,
        funBgMarketing,
        borderDefault,
        borderSecondaryButton,
        borderSelected,
        borderError,
        borderDonating,
        buttonPrimary,
        buttonSecure,
        buttonSecondary,
    )

    fun updateColorsFrom(other: CatchColors) {
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        textTertiary = other.textTertiary
        textDisabled = other.textDisabled
        textButton = other.textButton
        bgPrimary = other.bgPrimary
        bgSecondary = other.bgSecondary
        bgTertiary = other.bgTertiary
        funTextEarning = other.funTextEarning
        funTextRedeeming = other.funTextRedeeming
        funTextAlert = other.funTextAlert
        funTextError = other.funTextError
        funBgRedeeming = other.funBgRedeeming
        funBgAlert = other.funBgAlert
        funBgError = other.funBgError
        funBgDonating = other.funBgDonating
        funBgMarketing = other.funBgMarketing
        borderDefault = other.borderDefault
        borderSecondaryButton = other.borderSecondaryButton
        borderSelected = other.borderSelected
        borderError = other.borderError
        borderDonating = other.borderDonating
        buttonPrimary = other.buttonPrimary
        buttonSecure = other.buttonSecure
        buttonSecondary = other.buttonSecondary
    }
}

internal object CatchRawColors {
    /** Greys */
    const val WHITE = 0xFFFFFFFF
    const val GREY_1 = 0xFFFCFCFD
    const val GREY_2 = 0xFFF6F7F8
    const val GREY_3 = 0xFFDCDDE5
    const val GREY_4 = 0xFFA1A4BA
    const val GREY_5 = 0xFF686D8D
    const val GREY_6 = 0xFF45485E
    const val GREY_7 = 0xFF232639
    const val BLACK = 0xFF000000

    /** Highlights */
    const val PINK_1 = 0xFFFEDDF1
    const val PINK_2 = 0xFFE1309C
    const val PURPLE_1 = 0xFFF9F6FE
    const val PURPLE_2 = 0xFF6B27DD
    const val GREEN_1 = 0xFFE6F4E9
    const val GREEN_2 = 0xFF0C7D25

    /** Functions */
    const val BLUE_1 = 0xFFE5F0FE
    const val BLUE_2 = 0xFF0867D5
    const val RED_1 = 0xFFFDE8EA
    const val RED_2 = 0xFFC11126

    /** Dark Mode */
    const val DARK_MODE_PINK = 0xFFE052A8
    const val DARK_MODE_GREEN = 0xFF0AB230

    /** Accents */
    const val ACCENT_YELLOW = 0xFFFBDA69
    const val ACCENT_TURQUOISE = 0xFF1BC4D1

    /** Shades-Tints */
    const val SHADE_PURPLE_DARK = 0xFF32166B
    const val SHADE_PURPLE_LIGHT = 0xFFE1D4F8
}

internal fun lightColors(mono: Boolean = false) = CatchColors(
    textPrimary = Color(CatchRawColors.GREY_7),
    textSecondary = Color(CatchRawColors.GREY_6),
    textTertiary = Color(CatchRawColors.GREY_5),
    textDisabled = Color(CatchRawColors.GREY_4),
    textButton = Color(CatchRawColors.WHITE),
    bgPrimary = Color(CatchRawColors.WHITE),
    bgSecondary = Color(CatchRawColors.GREY_1),
    bgTertiary = Color(CatchRawColors.GREY_2),
    funTextEarning = if (mono) Color(CatchRawColors.GREY_7) else Color(CatchRawColors.PINK_2),
    funTextRedeeming = if (mono) Color(CatchRawColors.GREY_7) else Color(CatchRawColors.GREEN_2),
    funTextAlert = Color(CatchRawColors.BLUE_2),
    funTextError = Color(CatchRawColors.RED_2),
    funBgRedeeming = Color(CatchRawColors.GREEN_1),
    funBgAlert = Color(CatchRawColors.BLUE_1),
    funBgError = Color(CatchRawColors.RED_1),
    funBgDonating = Color(CatchRawColors.PURPLE_1),
    funBgMarketing = Color(CatchRawColors.SHADE_PURPLE_DARK),
    borderDefault = Color(CatchRawColors.GREY_3),
    borderSecondaryButton = Color(CatchRawColors.GREY_5),
    borderSelected = Color(CatchRawColors.GREY_7),
    borderError = Color(CatchRawColors.RED_2),
    borderDonating = Color(CatchRawColors.PURPLE_2),
    buttonPrimary = Color(CatchRawColors.PINK_2),
    buttonSecure = Color(CatchRawColors.BLUE_2),
    buttonSecondary = Color(CatchRawColors.WHITE),
)

internal fun darkColors(mono: Boolean = false) = CatchColors(
    textPrimary = Color(CatchRawColors.WHITE),
    textSecondary = Color(CatchRawColors.GREY_1),
    textTertiary = Color(CatchRawColors.GREY_2),
    textDisabled = Color(CatchRawColors.GREY_3),
    textButton = Color(CatchRawColors.BLACK),
    bgPrimary = Color(CatchRawColors.BLACK),
    bgSecondary = Color(CatchRawColors.GREY_7),
    bgTertiary = Color(CatchRawColors.GREY_6),
    funTextEarning = if (mono) Color(CatchRawColors.WHITE) else Color(CatchRawColors.DARK_MODE_PINK),
    funTextRedeeming = if (mono) Color(CatchRawColors.WHITE) else Color(CatchRawColors.DARK_MODE_GREEN),
    funTextAlert = Color(CatchRawColors.BLUE_2),
    funTextError = Color(CatchRawColors.RED_2),
    funBgRedeeming = Color(CatchRawColors.GREEN_1),
    funBgAlert = Color(CatchRawColors.BLUE_1),
    funBgError = Color(CatchRawColors.RED_1),
    funBgDonating = Color(CatchRawColors.PURPLE_1),
    funBgMarketing = Color(CatchRawColors.SHADE_PURPLE_DARK),
    borderDefault = Color(CatchRawColors.GREY_4),
    borderSecondaryButton = Color(CatchRawColors.GREY_5),
    borderSelected = Color(CatchRawColors.GREY_1),
    borderError = Color(CatchRawColors.RED_2),
    borderDonating = Color(CatchRawColors.PURPLE_2),
    buttonPrimary = Color(CatchRawColors.DARK_MODE_PINK),
    buttonSecure = Color(CatchRawColors.BLUE_2),
    buttonSecondary = Color(CatchRawColors.BLACK),
)
