package com.getcatch.android.ui.composables.elements

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

internal data class TooltipShape(
    val cornerRadius: Dp,
    val arrowSize: Dp
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        with(density) {

            val path = Path().apply {
                val radius = cornerRadius.toPx()
                val arrowHeight = arrowSize.toPx()
                val arrowWidth = arrowHeight * 2
                val bottomRight =
                    Offset(0f, 0f) + Offset(size.width, size.height) - Offset(0f, arrowHeight)
                val rect = Rect(Offset(0f, 0f), bottomRight)

                // Draw the rectangle with rounded corners
                addRoundRect(RoundRect(rect, radius, radius))

                // Draw the arrow
                moveTo(size.width / 2 - arrowWidth / 2, size.height - arrowHeight)
                lineTo(size.width / 2, size.height)
                lineTo(size.width / 2 + arrowWidth / 2, size.height - arrowHeight)
                close()
            }
            return Outline.Generic(path)
        }
    }
}
