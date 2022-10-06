package com.getcatch.android.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints

internal fun Modifier.constrainToParentHeight(): Modifier = layout { measurable, constraints ->
    if (constraints.maxHeight == Constraints.Infinity) {
        layout(0, 0) {}
    } else {
        val placeable = measurable.measure(constraints)
        layout(placeable.width, placeable.height) {
            placeable.place(0, 0)
        }
    }
}
