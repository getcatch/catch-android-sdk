package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.getcatch.android.ui.theming.CatchTheme

@Composable
internal fun WidgetTooltip(
    widgetWidth: Dp,
    onDismissRequested: () -> Unit,
    content: @Composable () -> Unit
) {
    Popup(
        popupPositionProvider = remember { TooltipPopupPositionProvider() },
        onDismissRequest = onDismissRequested,
    ) {
        Surface(

            modifier = Modifier.width(widgetWidth),
            color = CatchTheme.colors.background,
            shape = TooltipShape(8.dp, 8.dp),
            elevation = 2.dp,
        ) {
            // Add bottom padding to compensate for the tooltip arrow
            Box(Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                content()
            }
        }
    }
}
