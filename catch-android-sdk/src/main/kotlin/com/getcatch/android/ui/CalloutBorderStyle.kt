package com.getcatch.android.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.styles.values.ColorValue

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

    public class Custom(color: ColorValue, radius: Float = 0f): CalloutBorderStyle() {
        override val shape: Shape = RoundedCornerShape(size = radius.dp)
        internal val color: Color = color.value
    }
}
