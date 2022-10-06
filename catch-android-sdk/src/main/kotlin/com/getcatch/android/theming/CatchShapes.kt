package com.getcatch.android.theming

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

public sealed class BorderStyle {
    internal abstract val shape: Shape

    public object Square : BorderStyle() {
        override val shape: Shape = RoundedCornerShape(size = 0.dp)
    }

    public object SlightRound : BorderStyle() {
        override val shape: Shape = RoundedCornerShape(size = 4.dp)
    }
}

// Remember that any changes made to this sealed class need to be updated in `CalloutView.kt` and
// `res/values/attrs.xml`
public sealed class CalloutBorderStyle {
    internal abstract val shape: Shape

    public object Square : CalloutBorderStyle() {
        override val shape: Shape = RoundedCornerShape(size = 0.dp)
    }

    public object SlightRound : CalloutBorderStyle() {
        override val shape: Shape = RoundedCornerShape(size = 4.dp)
    }

    public object Pill : CalloutBorderStyle() {
        override val shape: Shape = RoundedCornerShape(percent = 50)
    }
}
