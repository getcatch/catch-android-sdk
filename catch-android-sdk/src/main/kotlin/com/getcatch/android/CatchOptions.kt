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
 * @property environment The `LIVE` environment should be used in
 * production applications while the `SANDBOX` environment should be
 * used for development and testing. Defaults to `SANDBOX`.
 *
 * @property customFontFamily An optional font family that will be
 * applied to all text in widgets if set. Defaults to null.
 *
 * @property styleConfig An optional style config that will be
 * applied to the elements of all widgets if set. Defaults to null.
 *
 * @property enableLogging A boolean flag to allow developers to control
 * the SDK's logging. The SDK does not handle nor log any personal data.
 * Recommended to only be enabled in debug builds. Defaults to false.
 */
public data class CatchOptions(
    val colorTheme: CatchColorTheme = CatchColorTheme.Light,
    val environment: Environment = Environment.SANDBOX,
    val customFontFamily: CustomFontFamily? = null,
    val styleConfig: CatchStyleConfig? = null,
    val enableLogging: Boolean = false,
)
