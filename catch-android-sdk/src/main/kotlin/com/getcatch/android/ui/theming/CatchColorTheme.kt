package com.getcatch.android.ui.theming

import com.getcatch.android.ui.theming.color.CatchComposeColors
import com.getcatch.android.ui.theming.color.CatchThemeColors

/**
 * Catch color themes provide the colors that will be used throughout
 * the Catch widgets in your app. There are four supported themes:
 *   - Light
 *   - LightMono
 *   - Dark
 *   - DarkMono
 *
 * For further color customization, see
 * [`CatchStyleConfig`](com.getcatch.android.ui.styles.CatchStyleConfig).
 */
public sealed class CatchColorTheme {
    internal abstract val colors: CatchThemeColors
    internal val composeColors: CatchComposeColors by lazy { this.colors.toComposeColors() }

    /** Utility flag to distinguish light themes from dark themes. */
    public abstract val isDarkTheme: Boolean

    /**
     * Intended for widgets which are displayed over a light background
     * and features Catch's branding color scheme.
     */
    public object Light : CatchColorTheme() {
        override val colors: CatchThemeColors = CatchThemeColors.Light
        override val isDarkTheme: Boolean = false
    }

    /**
     * Intended for widgets which are displayed over a light background
     * and features a monochromatic scheme.
     */
    public object LightMono : CatchColorTheme() {
        override val colors: CatchThemeColors = CatchThemeColors.LightMono
        override val isDarkTheme: Boolean = false
    }

    /**
     * Intended for widgets which are displayed over a dark background
     * and features Catch's branding color scheme.
     */
    public object Dark : CatchColorTheme() {
        override val colors: CatchThemeColors = CatchThemeColors.Dark
        override val isDarkTheme: Boolean = true
    }

    /**
     * Intended for widgets which are displayed over a dark background
     * and features a monochromatic scheme.
     */
    public object DarkMono : CatchColorTheme() {
        override val colors: CatchThemeColors = CatchThemeColors.DarkMono
        override val isDarkTheme: Boolean = true
    }
}
