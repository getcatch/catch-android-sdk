package com.getcatch.sample.ui.theming

import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

const val LIGHT_SURFACE = 0xFFFFFFFF
const val LIGHT_BACKGROUND = 0xFFDDDDDD
const val LIGHT_SECONDARY = 0xFFF7F7F7
const val DARK_SURFACE = 0xFF232639
const val DARK_BACKGROUND = 0xFF1C1311
const val DARK_SECONDARY = 0xFF121212
const val DARK_ON_SECONDARY = 0xFFCCCCCC

val LightDemoColors = lightColors(
    surface = Color(LIGHT_SURFACE),
    background = Color(LIGHT_BACKGROUND),
    secondary = Color(LIGHT_SECONDARY),
)

val DarkDemoColors = darkColors(
    surface = Color(DARK_SURFACE),
    background = Color(DARK_BACKGROUND),
    secondary = Color(DARK_SECONDARY),
    onSecondary = Color(DARK_ON_SECONDARY)
)

val demoCheckboxColors: CheckboxColors
    @Composable get() = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colors.primary,
        uncheckedColor = MaterialTheme.colors.onSecondary,
    )
