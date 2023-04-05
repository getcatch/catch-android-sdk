package com.getcatch.android

import androidx.compose.ui.text.font.FontFamily
import com.getcatch.android.network.Environment
import com.getcatch.android.styling.CatchStyleConfig
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.ui.theming.ThemeVariantOption

/**
 * An object which specifies optional configuration settings to
 * control the global behavior of the Catch SDK.
 *
 * @property colorTheme The default theme to be applied to all
 * Catch widgets. Defaults to `ThemeVariant.Light`.
 *
 * @property environment The production environment should be used
 * in live applications while the sandbox environment should be
 * used for development and testing. Defaults to sandbox.
 *
 * @property customFontFamily An optional font family that will be
 * applied to all text in widgets if set. Defaults to null.
 *
 * @property styleConfig An optional style config that will be
 * applied to all text in widgets if set. Defaults to null.
 */
public data class CatchOptions(
    val colorTheme: ThemeVariantOption = ThemeVariant.Light,
    val environment: Environment = Environment.SANDBOX,
    val customFontFamily: FontFamily? = null,
    val styleConfig: CatchStyleConfig? = null,
)
