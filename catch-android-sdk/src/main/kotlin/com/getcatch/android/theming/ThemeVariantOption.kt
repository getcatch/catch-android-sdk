package com.getcatch.android.theming

import com.getcatch.android.R

public sealed class ThemeVariantOption {
    public object Dynamic : ThemeVariantOption()
    public object DynamicMono : ThemeVariantOption()
}

public sealed class ThemeVariant : ThemeVariantOption() {
    internal abstract val colors: CatchColors
    internal abstract val logoResId: Int
    internal abstract val themeConfig: ThemeConfig

    public object Light : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.Light }
        override val logoResId: Int = R.drawable.ic_catch_logo_dark
        override val themeConfig by lazy { StandardThemeConfigs.Light }
    }

    public object LightMono : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.LightMono }
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_dark
        override val themeConfig by lazy { StandardThemeConfigs.LightMono }
    }

    public object Dark : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.Dark }
        override val logoResId: Int = R.drawable.ic_catch_logo_white
        override val themeConfig by lazy { StandardThemeConfigs.Dark }
    }

    public object DarkMono : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.DarkMono }
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_white
        override val themeConfig by lazy { StandardThemeConfigs.DarkMono }
    }
}
