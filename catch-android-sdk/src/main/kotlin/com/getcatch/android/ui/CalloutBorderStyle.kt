package com.getcatch.android.ui

import com.getcatch.android.ui.styles.values.ColorValue


public sealed interface CalloutBorderStyle {

    public object Square : CalloutBorderStyle
    public object SlightRound : CalloutBorderStyle

    public object Pill : CalloutBorderStyle

    public class Custom(
        internal val color: ColorValue, internal val radius: Float = 0f
    ) : CalloutBorderStyle

    public object None : CalloutBorderStyle
}
