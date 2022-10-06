package com.getcatch.android

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontFamily
import com.getcatch.android.exceptions.CatchNotInitializedException
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.theming.ThemeVariantOption
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

public object Catch {
    private var publicKey: String? = null

    public fun initialize(publicKey: String): Unit = synchronized(this) {
        this.publicKey = publicKey
        MainScope().launch {
            MerchantRepository.loadMerchant(publicKey = publicKey)
        }
    }

    public fun greet(): String = synchronized(this) {
        if (publicKey == null) {
            throw CatchNotInitializedException()
        }
        return "Hello from Catch!"
    }

    private val _customFontFamily: MutableState<FontFamily> =
        mutableStateOf(CatchTypography.circularFontFamily)
    internal val customFontFamily: State<FontFamily> = _customFontFamily
    public fun setCustomFontFamily(fontFamily: FontFamily): Unit = synchronized(this) {
        _customFontFamily.value = fontFamily
    }

    private val _colorTheme: MutableState<ThemeVariantOption> =
        mutableStateOf(ThemeVariantOption.Dynamic)
    internal val colorTheme: State<ThemeVariantOption> = _colorTheme
    public fun setColorTheme(themeVariantOption: ThemeVariantOption): Unit = synchronized(this) {
        _colorTheme.value = themeVariantOption
    }
}
