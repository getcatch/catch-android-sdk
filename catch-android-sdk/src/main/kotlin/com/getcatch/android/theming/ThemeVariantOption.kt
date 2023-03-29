package com.getcatch.android.theming

import com.getcatch.android.R
import com.getcatch.android.theming.color.CatchColorTheme
import com.getcatch.android.theming.color.CatchComposeColors

public sealed interface ThemeVariantOption

public sealed class DynamicThemeVariant : ThemeVariantOption {
    internal abstract val lightVariant: ThemeVariant
    internal abstract val darkVariant: ThemeVariant

    public object Standard : DynamicThemeVariant() {
        override val lightVariant: ThemeVariant by lazy { ThemeVariant.Light }
        override val darkVariant: ThemeVariant by lazy { ThemeVariant.Dark }
    }

    public object Mono : DynamicThemeVariant() {
        override val lightVariant: ThemeVariant by lazy { ThemeVariant.Light }
        override val darkVariant: ThemeVariant by lazy { ThemeVariant.Dark }
    }
}

public sealed class ThemeVariant : ThemeVariantOption {
    internal abstract val colors: CatchColorTheme
    internal abstract val logoResId: Int
    internal val composeColors: CatchComposeColors by lazy { this.colors.toComposeColors() }

    public object Light : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.Light
        override val logoResId: Int = R.drawable.ic_catch_logo_dark
    }

    public object LightMono : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.LightMono
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_dark
    }

    public object Dark : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.Dark
        override val logoResId: Int = R.drawable.ic_catch_logo_white
    }

    public object DarkMono : ThemeVariant() {
        override val colors: CatchColorTheme = CatchColorTheme.DarkMono
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_white
    }
}
