package com.getcatch.sample

import android.app.Application
import com.getcatch.android.Catch
import com.getcatch.android.CatchOptions
import com.getcatch.android.styling.CatchStyleConfig
import com.getcatch.android.styling.InfoWidgetStyle
import com.getcatch.android.styling.TextStyle
import com.getcatch.android.styling.values.FontWeight
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Catch.initialize(
            publicKey = "TEST_MERCHANT_PUBLIC_KEY",
            context = this,
            options = CatchOptions(
                styleConfig = CatchStyleConfig(
                    textStyle = TextStyle(fontSize = 20f, fontWeight = FontWeight.W100),
                    calloutStyle = InfoWidgetStyle(
                        textStyle = TextStyle(fontWeight = FontWeight.W900)
                    )
                )
            )
        )
    }
}
