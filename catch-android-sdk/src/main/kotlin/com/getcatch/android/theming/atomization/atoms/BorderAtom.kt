package com.getcatch.android.theming.atomization.atoms

import androidx.compose.ui.graphics.Color
import com.getcatch.android.theming.ThemeConfig

/**
 * Attributes that can be customized on widget with a border.
 */
public data class BorderAtom(
    /**
     * Applies to the border around the element.
     */
    val borderColor: Color? = null,

    /**
     * Applies to the border around the element.
     */
    val borderRadius: Int? = null,
) {
    internal data class Resolved(
        val borderColor: Color,
        // Nullable because border radius is also configurable via border styles on a widget
        val borderRadius: Int?,
    ) {
        fun withOverrides(overrides: BorderAtom?): Resolved {
            if (overrides == null) return this
            return Resolved(
                borderColor = overrides.borderColor ?: borderColor,
                borderRadius = overrides.borderRadius ?: borderRadius,
            )
        }
    }

    internal companion object {
        fun defaults(theme: ThemeConfig): Resolved {
            return Resolved(
                borderColor = theme.primaryStrokeColor,
                borderRadius = null,
            )
        }
    }
}
