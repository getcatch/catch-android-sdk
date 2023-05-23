package com.getcatch.sample

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.getcatch.android.Catch
import com.getcatch.android.ui.theming.CatchColorTheme

class DemoSettingsViewModel : ViewModel() {
    var colorTheme by mutableStateOf<CatchColorTheme>(CatchColorTheme.Light)
        private set

    var price by mutableStateOf(0)
        private set

    init {
        updateCatchColorTheme(colorTheme = CatchColorTheme.Light)
    }

    fun updateCatchColorTheme(colorTheme: CatchColorTheme) {
        this.colorTheme = colorTheme
        Catch.setColorTheme(colorTheme)
    }

    fun clearPrice() {
        price = 0
    }

    fun updatePrice(newPrice: Int) {
        price = newPrice
    }
}
