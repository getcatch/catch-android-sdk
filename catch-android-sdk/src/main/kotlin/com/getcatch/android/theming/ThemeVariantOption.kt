package com.getcatch.android.theming

import com.getcatch.android.R

public sealed class ThemeVariantOption {
    public object Dynamic : ThemeVariantOption()
    public object DynamicMono : ThemeVariantOption()
}

public sealed class ThemeVariant : ThemeVariantOption() {
    internal abstract val colors: CatchColors
    internal abstract val logoResId: Int

    public object Light : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.Light }
        override val logoResId: Int = R.drawable.ic_catch_logo_dark
    }

    public object LightMono : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.LightMono }
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_dark
    }

    public object Dark : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.Dark }
        override val logoResId: Int = R.drawable.ic_catch_logo_white
    }

    public object DarkMono : ThemeVariant() {
        override val colors: CatchColors by lazy { CatchColorThemes.DarkMono }
        override val logoResId: Int = R.drawable.ic_catch_logo_mono_white
    }
}
