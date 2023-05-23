package com.getcatch.android.ui

import com.getcatch.android.ui.styles.values.ColorValue

/**
 * The options for the style of border the [`Callout`](com.getcatch.android.ui.composables.Callout)
 * widget renders.
 *
 * The only difference between this and the [`BorderStyle`](BorderStyle) is that the
 * [`Callout`](com.getcatch.android.ui.composables.Callout) widget
 * also supports a pill shaped border.
 */
public sealed interface CalloutBorderStyle {

    /** Renders a border with square corners. */
    public object Square : CalloutBorderStyle

    /** Renders a border with slightly rounded corners. */
    public object SlightRound : CalloutBorderStyle

    /** Renders a border with fully rounded corners. */
    public object Pill : CalloutBorderStyle

    /** Renders a border in the specified color and with the specified radius. */
    public class Custom(
        internal val color: ColorValue, internal val radius: Float = 0f
    ) : CalloutBorderStyle

    /** Renders widgets with no borders and no padding around the internal content. */
    public object None : CalloutBorderStyle
}
