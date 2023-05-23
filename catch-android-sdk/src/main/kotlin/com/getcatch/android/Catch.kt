package com.getcatch.android

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.getcatch.android.di.sdkModule
import com.getcatch.android.exceptions.CatchNotInitializedException
import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.repository.MerchantRepository
import com.getcatch.android.repository.UserRepository
import com.getcatch.android.ui.styles.CatchStyleConfig
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.ui.typography.CatchFonts
import com.getcatch.android.ui.typography.CustomFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.concurrent.atomic.AtomicBoolean

/** The Catch SDK global interface. */
public object Catch {
    private val initialized = AtomicBoolean(false)
    internal var styleConfig: CatchStyleConfig? = null

    private val _customFontFamily: MutableState<FontFamily> =
        mutableStateOf(CatchFonts.circularFontFamily)
    internal val customFontFamily: State<FontFamily> = _customFontFamily

    private val _colorTheme: MutableState<CatchColorTheme> =
        mutableStateOf(CatchColorTheme.Light)
    internal val colorTheme: State<CatchColorTheme> = _colorTheme

    /**
     * Initializes the Catch SDK. Must be called from the
     * [`onStart`](https://developer.android.com/reference/android/app/Application#onCreate())
     * method of your
     * [`Application`](https://developer.android.com/reference/android/app/Application) class.
     *
     * @param context The Android Context of the application.
     *
     * @param publicKey A string representing the merchant's public API key.
     *
     * @param options An object which specifies optional configuration settings to control the
     * global behavior of the Catch SDK. If the options object is omitted, the Catch SDK will
     * fallback to default values.
     */
    public fun initialize(
        context: Context,
        publicKey: String,
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
        initialized.set(true)
    }

    /**
     * Changes the current value of the global font family. The font family can also be specified
     * when initializing the SDK.
     *
     * @param fontFamily An object containing all the necessary font information.
     */
    public fun setCustomFontFamily(fontFamily: CustomFontFamily): Unit = synchronized(this) {
        _customFontFamily.value = fontFamily.composeFontFamily
    }

    /**
     * Changes the current value of the global theme variant. The `colorTheme` parameter accepts
     * the same enumeration of values that can be used for the theme option when initializing the
     * SDK.
     *
     * @param colorTheme The Catch preset color theme.
     */
    public fun setColorTheme(colorTheme: CatchColorTheme): Unit = synchronized(this) {
        _colorTheme.value = colorTheme
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

    private suspend fun refreshMerchant(
        merchantRepo: MerchantRepository,
        userRepo: UserRepository
    ) {
        val response = merchantRepo.loadMerchant()

        if (response is NetworkResponse.Failure) {
            Log.e("CatchSDK", "Failed to fetch merchant.")
            // Assume a new user so rewards can be calculated
            userRepo.fallbackToNewUser()
        }
    }

    private suspend fun refreshUser(merchantRepo: MerchantRepository, userRepo: UserRepository) {
        combine(merchantRepo.activeMerchant, userRepo.deviceToken) { merchant, token ->
            Pair(
                merchant,
                token
            )
        }.collect { (merchant, _) ->
            if (merchant != null) {
                userRepo.loadUserData(merchantId = merchant.id)
            }
        }
    }

    private fun loadData(merchantRepo: MerchantRepository, userRepo: UserRepository) {
        val applicationJob = SupervisorJob()
        val backgroundScope = CoroutineScope(Dispatchers.IO + applicationJob)
        var refreshMerchantJob: Job? = null
        var refreshUserJob: Job? = null
        ProcessLifecycleOwner.get().lifecycle.addObserver(LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    refreshMerchantJob = backgroundScope.launch {
                        refreshMerchant(merchantRepo, userRepo)
                    }
                    refreshUserJob = backgroundScope.launch {
                        refreshUser(merchantRepo, userRepo)
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    refreshMerchantJob?.cancel()
                    refreshUserJob?.cancel()
                }

                else -> { /* No-op */ }
            }
        }
        )
    }

    private fun applyOptions(options: CatchOptions) {
        if (options.customFontFamily != null) {
            _customFontFamily.value = options.customFontFamily.composeFontFamily
        }
        _colorTheme.value = options.colorTheme
        styleConfig = options.styleConfig
    }

    internal fun assertInitialized() {
        if (!initialized.get()) {
            throw CatchNotInitializedException()
        }
    }
}
