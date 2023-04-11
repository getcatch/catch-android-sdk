package com.getcatch.sample

import android.app.Application
import com.getcatch.android.Catch
import com.getcatch.android.CatchOptions
import com.getcatch.android.network.Environment
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Catch.initialize(
            publicKey = "TEST_MERCHANT_PUBLIC_KEY",
            context = this,
            options = CatchOptions(
                environment = Environment.SANDBOX,
            )
        )
    }
}
