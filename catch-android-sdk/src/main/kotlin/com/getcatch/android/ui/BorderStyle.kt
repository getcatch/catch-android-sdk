package com.getcatch.android.ui

import com.getcatch.android.ui.styles.values.ColorValue

public sealed interface BorderStyle {

    public object Square : BorderStyle

    public object SlightRound : BorderStyle

    public class Custom(
        internal val color: ColorValue, internal val radius: Float = 0f
    ) : BorderStyle

    public object None : BorderStyle
}
