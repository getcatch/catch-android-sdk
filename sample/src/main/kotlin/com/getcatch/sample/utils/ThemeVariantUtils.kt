package com.getcatch.sample.utils

import com.getcatch.android.ui.theming.ThemeVariant

val allThemeVariants = listOf(
    ThemeVariant.Light,
    ThemeVariant.LightMono,
    ThemeVariant.Dark,
    ThemeVariant.DarkMono
)

val ThemeVariant.name: String
    get() = when (this) {
        ThemeVariant.Light -> "Light"
        ThemeVariant.LightMono -> "Light mono"
        ThemeVariant.Dark -> "Dark"
        ThemeVariant.DarkMono -> "Dark mono"
    }
