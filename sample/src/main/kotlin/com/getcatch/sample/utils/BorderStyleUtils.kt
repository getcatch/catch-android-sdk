package com.getcatch.sample.utils

import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.CalloutBorderStyle

val calloutBorderStyleDemoOptions = listOf(
    CalloutBorderStyle.None,
    CalloutBorderStyle.SlightRound,
    CalloutBorderStyle.Pill,
    CalloutBorderStyle.Square
)

val CalloutBorderStyle.name: String
    get() = when (this) {
        CalloutBorderStyle.None -> "None"
        CalloutBorderStyle.SlightRound -> "Rounded"
        CalloutBorderStyle.Pill -> "Pill"
        CalloutBorderStyle.Square -> "Square"
        is CalloutBorderStyle.Custom -> "Custom"
    }

val infoWidgetBorderStyleDemoOptions = listOf(
    BorderStyle.None,
    BorderStyle.SlightRound,
    BorderStyle.Square
)

val actionWidgetBorderStyleDemoOptions = listOf(
    BorderStyle.SlightRound,
    BorderStyle.Square,
    BorderStyle.None
)

val BorderStyle.name: String
    get() = when (this) {
        BorderStyle.None -> "None"
        BorderStyle.SlightRound -> "Rounded"
        BorderStyle.Square -> "Square"
        is BorderStyle.Custom -> "Custom"
    }
