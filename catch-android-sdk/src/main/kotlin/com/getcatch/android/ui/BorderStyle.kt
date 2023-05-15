package com.getcatch.android.ui

import com.getcatch.android.ui.styles.values.ColorValue

/** The options for the style of border the widgets render.*/
public sealed interface BorderStyle {

    /** Renders a border with square corners. */
    public object Square : BorderStyle

    /** Renders a border with slightly rounded corners. */
    public object SlightRound : BorderStyle

    /** Renders a border in the specified color and with the specified radius. */
    public class Custom(
        internal val color: ColorValue, internal val radius: Float = 0f
    ) : BorderStyle

    /** Renders widgets with no borders and no padding around the internal content. */
    public object None : BorderStyle
}
