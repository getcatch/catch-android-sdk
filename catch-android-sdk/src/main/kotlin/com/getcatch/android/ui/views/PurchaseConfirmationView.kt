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
import com.getcatch.android.ui.composables.PurchaseConfirmation
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.utils.getBorderStyle
import com.getcatch.android.utils.getCatchColorTheme

/**
 * The `PurchaseConfirmation` widget is designed to be used on the merchant's order
 * confirmation page if Catch was used as a payment method.
 *
 * The widget includes information about how much credit the consumer just earned
 * through their purchase and contains a link which directs the consumer to their account
 * page on Catch's website.
 *
 * For virtual card integrations, use
 * [`PurchaseConfirmationByOrderIdView`](PurchaseConfirmationByOrderIdView).
 */
public class PurchaseConfirmationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _earned: MutableState<Int> = mutableStateOf(0)
    private val _donation: MutableState<Int> = mutableStateOf(0)
    private val _borderStyle: MutableState<BorderStyle> = mutableStateOf(BorderStyle.SlightRound)
    private val _colorTheme: MutableState<CatchColorTheme?> = mutableStateOf(null)
    private val _styleOverrides: MutableState<ActionWidgetStyle?> = mutableStateOf(null)

    /** The amount in cents that that the consumer earned in credit based on their purchase.*/
    public var earned: Int by _earned

    /**
     * The amount of cents that the consumer is donating. Not used if the merchant
     * doesn't have donations enabled.
     */
    public var donation: Int by _donation

    /**
     * The [`BorderStyle`](BorderStyle) that the widget renders.
     * Defaults to the [`BorderStyle.SlightRound`](BorderStyle.SlightRound) style.
     */
    public var borderStyle: BorderStyle by _borderStyle

    /**
     * The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set globally on
     * the [`Catch`](com.getcatch.androidCatch) object will be used, which defaults to
     * [`CatchColorTheme.Light`](CatchColorTheme.Light).
     */
    public var colorTheme: CatchColorTheme? by _colorTheme

    /**
     * Style overrides which can be used to override the widget's default
     * appearance (ex. font size, color, weight, etc.).
     */
    public var styleOverrides: ActionWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.PurchaseConfirmationView, 0, 0)
            .apply {
                try {
                    getBorderStyle(
                        widgetName = WIDGET_NAME,
                        borderStyleResId = R.styleable.PurchaseConfirmationView_borderStyle,
                        customBorderColorResId = R.styleable.PurchaseConfirmationView_customBorderColor,
                        customBorderRadiusResId = R.styleable.PurchaseConfirmationView_customBorderRadius,
                    )?.let {
                        _borderStyle.value = it
                    }
                    _colorTheme.value =
                        getCatchColorTheme(R.styleable.PurchaseConfirmationView_colorTheme)
                } finally {
                    recycle()
                }
            }
    }

    @Composable
    override fun Content() {
        PurchaseConfirmation(
            earned = _earned.value,
            donation = _donation.value,
            borderStyle = _borderStyle.value,
            colorTheme = _colorTheme.value,
            styleOverrides = _styleOverrides.value,
        )
    }

    internal companion object {
        const val WIDGET_NAME = "PurchaseConfirmationView"
    }
}
