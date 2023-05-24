package com.getcatch.android.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import com.getcatch.android.R
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.composables.PurchaseConfirmationByOrderId
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.utils.getBorderStyle
import com.getcatch.android.utils.getCatchColorTheme

/**
 * The `PurchaseConfirmationByOrderId` widget serves the same purpose as the
 * [`PurchaseConfirmationView`](PurchaseConfirmationView) widget but is to
 * be used for purchases made with Catch's virtual card integration.
 */
public class PurchaseConfirmationByOrderIdView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _orderId: MutableState<String?> = mutableStateOf(null)
    private val _borderStyle: MutableState<BorderStyle> = mutableStateOf(BorderStyle.SlightRound)
    private val _colorTheme: MutableState<CatchColorTheme?> = mutableStateOf(null)
    private val _styleOverrides: MutableState<ActionWidgetStyle?> = mutableStateOf(null)

    /**
     * The order ID used to create the virtual card checkout for which the
     * purchase confirmation will be displayed.
     */
    public var orderId: String? by _orderId

    /**
     * The [`BorderStyle`](BorderStyle) that the widget renders.
     * Defaults to the [`BorderStyle.SlightRound`](BorderStyle.SlightRound) style.
     */
    public var borderStyle: BorderStyle by _borderStyle

    /**
     * The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set globally on
     * the [`Catch`](com.getcatch.android.Catch) object will be used, which defaults to
     * [`CatchColorTheme.Light`](CatchColorTheme.Light).
     */
    public var colorTheme: CatchColorTheme? by _colorTheme

    /**
     * Style overrides which can be used to override the widget's default
     * appearance (ex. font size, color, weight, etc.).
     */
    public var styleOverrides: ActionWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PurchaseConfirmationByOrderIdView,
            0,
            0
        )
            .apply {
                try {
                    getBorderStyle(
                        widgetName = WIDGET_NAME,
                        borderStyleResId = R.styleable.PurchaseConfirmationByOrderIdView_borderStyle,
                        customBorderColorResId = R.styleable.PurchaseConfirmationByOrderIdView_customBorderColor,
                        customBorderRadiusResId = R.styleable.PurchaseConfirmationByOrderIdView_customBorderRadius,
                    )?.let {
                        _borderStyle.value = it
                    }
                    _colorTheme.value =
                        getCatchColorTheme(R.styleable.PurchaseConfirmationByOrderIdView_colorTheme)
                } finally {
                    recycle()
                }
            }
    }

    @Composable
    override fun Content() {
        _orderId.value?.let {
            PurchaseConfirmationByOrderId(
                orderId = it,
                borderStyle = _borderStyle.value,
                colorTheme = _colorTheme.value,
                styleOverrides = _styleOverrides.value,
            )
        }
    }

    internal companion object {
        const val WIDGET_NAME = "PurchaseConfirmationByOrderIdView"
    }
}
