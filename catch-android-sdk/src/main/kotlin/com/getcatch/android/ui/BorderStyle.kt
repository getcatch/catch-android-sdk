package com.getcatch.android.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.styles.values.ColorValue

public sealed interface BorderStyle {

    public object Square : BorderStyle, HasBorderShape {
        override val shape: Shape = RoundedCornerShape(size = 0.dp)
    }

    public object SlightRound : BorderStyle, HasBorderShape {
        override val shape: Shape = RoundedCornerShape(size = 4.dp)
    }

    public class Custom(color: ColorValue, radius: Float = 0f): BorderStyle, HasBorderShape {
        override val shape: Shape = RoundedCornerShape(size = radius.dp)
        internal val color: Color = color.value
    }

    public object None: BorderStyle
}
