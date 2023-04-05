package com.getcatch.android

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontFamily
import com.getcatch.android.di.sdkModule
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.repository.UserRepository
import com.getcatch.android.ui.styles.CatchStyleConfig
import com.getcatch.android.ui.theming.DynamicThemeVariant
import com.getcatch.android.ui.theming.ThemeVariantOption
import com.getcatch.android.ui.typography.CatchFonts
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

public object Catch {
    public fun initialize(
        publicKey: String,
        context: Context,
        options: CatchOptions = CatchOptions(),
    ): Unit = synchronized(this) {
        // Setup dependency injection
        val koinApp = initKoin(
            context = context,
            publicKey = publicKey,
            environment = options.environment
        )

        // Load merchant and user data
        val merchantRepo = koinApp.koin.get<MerchantRepository>()
        val userRepo = koinApp.koin.get<UserRepository>()
        loadData(merchantRepo, userRepo)

        // Apply custom options
        applyOptions(options)
    }

    private fun initKoin(
        context: Context,
        publicKey: String,
        environment: Environment
    ): KoinApplication = startKoin {
        androidContext(context)
        modules(
            module {
                single { PublicKey(value = publicKey) }
                single { environment }
            },
            sdkModule,
        )
    }

    private fun loadData(merchantRepo: MerchantRepository, userRepo: UserRepository) {
        // Load merchant on app start
        MainScope().launch {
            when (val response = merchantRepo.loadMerchant()) {
                is NetworkResponse.Success -> {
                    val merchant = response.body
                    userRepo.loadUserData(merchant.id)
                }
                is NetworkResponse.Failure -> {
                    Log.e("Catch", "Failed to fetch merchant.")
                    // Assume a new user so rewards can be calculated
                    userRepo.fallbackToNewUser()
                }
            }
        }
    }

    private fun applyOptions(options: CatchOptions) {
        if (options.customFontFamily != null) {
            _customFontFamily.value = options.customFontFamily
        }
        _colorTheme.value = options.colorTheme
        styleConfig = options.styleConfig
    }

    internal var styleConfig: CatchStyleConfig? = null

    private val _customFontFamily: MutableState<FontFamily> =
        mutableStateOf(CatchFonts.circularFontFamily)
    internal val customFontFamily: State<FontFamily> = _customFontFamily
    public fun setCustomFontFamily(fontFamily: FontFamily): Unit = synchronized(this) {
        _customFontFamily.value = fontFamily
    }

    private val _colorTheme: MutableState<ThemeVariantOption> =
        mutableStateOf(DynamicThemeVariant.Standard)
    internal val colorTheme: State<ThemeVariantOption> = _colorTheme
    public fun setColorTheme(themeVariantOption: ThemeVariantOption): Unit = synchronized(this) {
        _colorTheme.value = themeVariantOption
    }
}
