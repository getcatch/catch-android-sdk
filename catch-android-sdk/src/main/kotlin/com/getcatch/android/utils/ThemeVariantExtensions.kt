package com.getcatch.android.utils

import com.getcatch.android.R
import com.getcatch.android.ui.theming.CatchColorTheme

internal val CatchColorTheme.logoResId: Int
    get() = when (this) {
        CatchColorTheme.Light -> R.drawable.ic_catch_logo_dark
        CatchColorTheme.LightMono -> R.drawable.ic_catch_logo_mono_dark
        CatchColorTheme.Dark -> R.drawable.ic_catch_logo_white
        CatchColorTheme.DarkMono -> R.drawable.ic_catch_logo_mono_white
    }
