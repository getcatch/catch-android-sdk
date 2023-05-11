package com.getcatch.android.utils

import com.getcatch.android.R
import com.getcatch.android.ui.theming.ThemeVariant

internal val ThemeVariant.logoResId: Int
    get() = when (this) {
        ThemeVariant.Light -> R.drawable.ic_catch_logo_dark
        ThemeVariant.LightMono -> R.drawable.ic_catch_logo_mono_dark
        ThemeVariant.Dark -> R.drawable.ic_catch_logo_white
        ThemeVariant.DarkMono -> R.drawable.ic_catch_logo_mono_white
    }
