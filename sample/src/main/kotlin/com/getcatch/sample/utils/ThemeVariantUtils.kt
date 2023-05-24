package com.getcatch.sample.utils

import com.getcatch.android.ui.theming.CatchColorTheme

val allCatchColorThemes = listOf(
    CatchColorTheme.Light,
    CatchColorTheme.LightMono,
    CatchColorTheme.Dark,
    CatchColorTheme.DarkMono
)

val CatchColorTheme.name: String
    get() = when (this) {
        CatchColorTheme.Light -> "Light"
        CatchColorTheme.LightMono -> "Light mono"
        CatchColorTheme.Dark -> "Dark"
        CatchColorTheme.DarkMono -> "Dark mono"
    }
