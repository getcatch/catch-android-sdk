package com.getcatch.android.theming

import com.getcatch.android.R
import com.getcatch.android.theming.color.CatchColorTheme
import com.getcatch.android.theming.color.CatchComposeColors

/**
 * The preset color themes used to render the Catch widgets. Theme variant
 * options come in [static (`ThemeVariant`)](com.getcatch.android.theming.ThemeVariant) and
 * [dynamic (`DynamicThemeVariant`)](com.getcatch.android.theming.DynamicThemeVariant) varieties.
 *
 * Widgets will default to the `ThemeVariant.Light` theme if no theme is set.
 */
public sealed interface ThemeVariantOption

/**
 * Dynamic theme variants allow for Catch widgets to follow the system's
 * current theme automatically.
 *
 *
 * Dynamic themes leverage the built-in dark theme feature introduced in
 * Android 10. When in Day mode, Catch widgets will use a light theme variant.
 * When in Night mode, Catch widgets will use a dark theme variant.
 *
 *
 * **Note:** If your app does not support dynamic theming, do ***not***
 * use a dynamic theme variant.
 *
 *
 * For more information on supporting dynamic theming, see
 * [the Android docs](https://developer.android.com/develop/ui/views/theming/darktheme).
 */
public sealed class DynamicThemeVariant : ThemeVariantOption {
    internal abstract val lightVariant: ThemeVariant
    internal abstract val darkVariant: ThemeVariant


    /**
     * Dynamically colors widgets according to the system's current theme
     * and features Catch's branding color scheme.
     */
    public object Standard : DynamicThemeVariant() {
        override val lightVariant: ThemeVariant by lazy { ThemeVariant.Light }
        override val darkVariant: ThemeVariant by lazy { ThemeVariant.Dark }
    }

    /**
     * Dynamically colors widgets according to the system's current theme
     * and features a monochromatic scheme.
     */
    public object Mono : DynamicThemeVariant() {
        override val lightVariant: ThemeVariant by lazy { ThemeVariant.Light }
        override val darkVariant: ThemeVariant by lazy { ThemeVariant.Dark }
    }
}

/**
 * Theme variants provide the colors that will be used throughout
 * the Catch widgets in your app. There are four supported theme variants:
 *   - Light
 *   - LightMono
 *   - Dark
 *   - DarkMono
 *
 * For further color customization, see `StyleOverrides`.
 */
public sealed class ThemeVariant : ThemeVariantOption {
    internal abstract val colors: CatchColorTheme
    internal abstract val logoResId: Int
    internal val composeColors: CatchComposeColors by lazy { this.colors.toComposeColors() }

    /**
     * Intended for widgets which are displayed over a light background
     * and features Catch's branding color scheme.
     */
    public object Light : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.Light
        override val logoResId: Int = R.drawable.ic_catch_logo_dark
    }

    /**
     * Intended for widgets which are displayed over a light background
     * and features a monochromatic scheme.
     */
    public object LightMono : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.LightMono
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_dark
    }

    /**
     * Intended for widgets which are displayed over a dark background
     * and features Catch's branding color scheme.
     */
    public object Dark : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.Dark
        override val logoResId: Int = R.drawable.ic_catch_logo_white
    }

    /**
     * Intended for widgets which are displayed over a dark background
     * and features a monochromatic scheme.
     */
    public object DarkMono : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.DarkMono
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_white
    }
}
