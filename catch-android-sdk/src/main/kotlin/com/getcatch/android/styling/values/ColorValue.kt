package com.getcatch.android.styling.values

import androidx.compose.ui.graphics.Color
import com.getcatch.android.utils.fromHexString


public class ColorValue {
    internal val value: Color

    public constructor(color: Int) {
        value = Color(color)
    }

    public constructor(color: Long) {
        value = Color(color)
    }

    public constructor(hexString: String) {
        value = Color.fromHexString(hexString)
    }

    public constructor(red: Int, green: Int, blue: Int, alpha: Int) {
        value = Color(red = red, green = green, blue = blue, alpha = alpha)
    }

    public constructor(red: Float, green: Float, blue: Float, alpha: Float) {
        value = Color(red = red, green = green, blue = blue, alpha = alpha)
    }
}
