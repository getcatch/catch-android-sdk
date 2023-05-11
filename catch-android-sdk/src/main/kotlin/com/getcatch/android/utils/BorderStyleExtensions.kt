package com.getcatch.android.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.theming.CatchTheme

internal val CalloutBorderStyle.shape: Shape?
    get() = when (this) {
        CalloutBorderStyle.Square -> RoundedCornerShape(size = 0.dp)
        CalloutBorderStyle.SlightRound -> RoundedCornerShape(size = 4.dp)
        CalloutBorderStyle.Pill -> RoundedCornerShape(percent = 50)
        is CalloutBorderStyle.Custom -> RoundedCornerShape(size = radius.dp)
        CalloutBorderStyle.None -> null
    }

internal val CalloutBorderStyle.color: Color?
    @Composable
    get() = when (this) {
        CalloutBorderStyle.Square,
        CalloutBorderStyle.SlightRound,
        CalloutBorderStyle.Pill -> CatchTheme.colors.border

        is CalloutBorderStyle.Custom -> color.value

        CalloutBorderStyle.None -> null
    }

internal val BorderStyle.shape: Shape?
    get() = when (this) {
        BorderStyle.Square -> RoundedCornerShape(size = 0.dp)
        BorderStyle.SlightRound -> RoundedCornerShape(size = 4.dp)
        is BorderStyle.Custom -> RoundedCornerShape(size = radius.dp)
        BorderStyle.None -> null
    }

internal val BorderStyle.color: Color?
    @Composable
    get() = when (this) {
        BorderStyle.Square,
        BorderStyle.SlightRound -> CatchTheme.colors.border

        is BorderStyle.Custom -> color.value

        BorderStyle.None -> null
    }
