package com.getcatch.sample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.getcatch.android.Catch
import com.getcatch.android.ui.theming.ThemeVariant

class DemoSettingsViewModel : ViewModel() {
    var themeVariant by mutableStateOf<ThemeVariant>(ThemeVariant.Light)
        private set

    var price by mutableStateOf(0)
        private set

    init {
        updateThemeVariant(variant = ThemeVariant.Light)
    }

    fun updateThemeVariant(variant: ThemeVariant) {
        themeVariant = variant
        Catch.setColorTheme(variant)
    }

    fun addTenDollars() {
        price += TEN_DOLLARS
    }

    fun subtractTenDollars() {
        price = (price - TEN_DOLLARS).coerceAtLeast(0)
    }

    fun addOneDollar() {
        price += ONE_DOLLAR
    }

    fun subtractOneDollar() {
        price = (price - ONE_DOLLAR).coerceAtLeast(0)
    }

    companion object {
        private const val TEN_DOLLARS = 1000 // Ten dollars in cents
        private const val ONE_DOLLAR = 100 // One dollar in cents
    }
}
