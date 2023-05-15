package com.getcatch.android.utils

import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.CatchLogoSize

internal fun Modifier.border(borderStyle: CalloutBorderStyle): Modifier = composed {
    val shape = borderStyle.shape
    val color = borderStyle.color
    if (shape != null && color != null) {
        border(1.dp, color, shape)
    } else {
        this
    }
}

internal fun Modifier.border(borderStyle: BorderStyle): Modifier = composed {
    val shape = borderStyle.shape
    val color = borderStyle.color
    if (shape != null && color != null) {
        border(1.dp, color, shape)
    } else {
        this
    }
}
