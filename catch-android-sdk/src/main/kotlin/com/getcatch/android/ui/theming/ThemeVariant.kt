package com.getcatch.android.ui.theming

import com.getcatch.android.ui.theming.color.CatchColorTheme
import com.getcatch.android.ui.theming.color.CatchComposeColors

/**
 * Theme variants provide the colors that will be used throughout
 * the Catch widgets in your app. There are four supported theme variants:
 *   - Light
 *   - LightMono
 *   - Dark
 *   - DarkMono
 *
 * For further color customization, see
 * [`CatchStyleConfig`](com.getcatch.android.ui.styles.CatchStyleConfig).
 */
public sealed class ThemeVariant {
    internal abstract val colors: CatchColorTheme
    internal val composeColors: CatchComposeColors by lazy { this.colors.toComposeColors() }

    /** Utility flag to distinguish light themes from dark themes. */
    public abstract val isDarkTheme: Boolean

    /**
     * Intended for widgets which are displayed over a light background
     * and features Catch's branding color scheme.
     */
    public object Light : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.Light
        override val isDarkTheme: Boolean = false
    }

    /**
     * Intended for widgets which are displayed over a light background
     * and features a monochromatic scheme.
     */
    public object LightMono : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.LightMono
        override val isDarkTheme: Boolean = false
    }

    /**
     * Intended for widgets which are displayed over a dark background
     * and features Catch's branding color scheme.
     */
    public object Dark : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.Dark
        override val isDarkTheme: Boolean = true
    }

    /**
     * Intended for widgets which are displayed over a dark background
     * and features a monochromatic scheme.
     */
    public object DarkMono : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.DarkMono
        override val isDarkTheme: Boolean = true
    }
}
