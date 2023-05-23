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

    fun clearPrice() {
        price = 0
    }

    fun updatePrice(newPrice: Int) {
        price = newPrice
    }
}
