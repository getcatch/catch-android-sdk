package com.getcatch.android

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontFamily
import com.getcatch.android.di.sdkModule
import com.getcatch.android.domain.PublicKey
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.theming.ThemeVariantOption
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

public object Catch {
    public fun initialize(publicKey: String, context: Context): Unit = synchronized(this) {
        // Setup dependency injection
        val koinApp = startKoin {
            androidContext(context)
            modules(
                module {
                    single { PublicKey(value = publicKey) }
                },
                sdkModule,
            )
        }
        val merchantRepo = koinApp.koin.get<MerchantRepository>()
        // Load merchant on app start
        MainScope().launch {
            merchantRepo.loadMerchant()
        }
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
