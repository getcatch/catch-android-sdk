package com.getcatch.android

import com.getcatch.android.network.Environment
import com.getcatch.android.ui.styles.CatchStyleConfig
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.ui.typography.CustomFontFamily

/**
 * An object which specifies optional configuration settings to
 * control the global behavior of the Catch SDK.
 *
 * @property colorTheme The default theme to be applied to all Catch widgets.
 * Defaults to [`CatchColorTheme.Light`](CatchColorTheme.Light).
 *
 * @property environment The production environment should be used
 * in live applications while the sandbox environment should be
 * used for development and testing. Defaults to sandbox.
 *
 * @property customFontFamily An optional font family that will be
 * applied to all text in widgets if set. Defaults to null.
 *
 * @property styleConfig An optional style config that will be
 * applied to the elements of all widgets if set. Defaults to null.
 */
public data class CatchOptions(
    val colorTheme: CatchColorTheme = CatchColorTheme.Light,
    val environment: Environment = Environment.SANDBOX,
    val customFontFamily: CustomFontFamily? = null,
    val styleConfig: CatchStyleConfig? = null,
)
