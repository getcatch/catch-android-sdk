package com.getcatch.android.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import com.getcatch.android.R
import com.getcatch.android.models.Item
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.composables.ExpressCheckoutCallout
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.utils.getBorderStyle
import com.getcatch.android.utils.getCatchColorTheme

/**
 * The `ExpressCheckoutCallout` widget displays similar informational content as the
 * [`Callout`](CalloutView) widget with additional messaging on where to find Catch
 * in the checkout flow.
 *
 * It is intended to be displayed in merchant checkout flows in which an express checkout option is
 * present. Since Catch can only be selected on the final step of checkout, this messaging is meant
 * to reduce confusion if the consumer intends to pay with Catch but does not see it displayed as an
 * express checkout option. The widget also includes a button to open an informational modal with
 * more details about paying with Catch and with links to visit Catch's marketing website.
 *
 * The `ExpressCheckoutCallout` widget makes use of its `price`, `items`, and `userCohorts`
 * properties to calculate rewards the user will earn on the current purchase.
 */
public class ExpressCheckoutCalloutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _price = mutableStateOf<Int?>(null)
    private val _items = mutableStateOf<List<Item>?>(null)
    private val _userCohorts = mutableStateOf<List<String>?>(null)
    private val _borderStyle = mutableStateOf<BorderStyle>(BorderStyle.None)
    private val _colorTheme = mutableStateOf<CatchColorTheme?>(null)
    private val _styleOverrides = mutableStateOf<InfoWidgetStyle?>(null)

    /**
     * The cost in cents that a consumer would pay for the item(s) without redeeming Catch credit.
     */
    public var price: Int? by _price

    /**
     * A list of user cohorts that the signed in user qualifies for. Used to calculate user cohort
     * based rewards.
     */
    public var userCohorts: List<String>? by _userCohorts

    /** The [BorderStyle] that the widget renders. Defaults to the [BorderStyle.None] style. */
    public var borderStyle: BorderStyle by _borderStyle

    /**
     * The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set globally on
     * the [`Catch`](com.android.getcatch.Catch) object will be used, which defaults to
     * [`CatchColorTheme.Light`](CatchColorTheme.Light).
     */
    public var colorTheme: CatchColorTheme? by _colorTheme

    /** A list of all items included in the order. Used to calculate item-based rewards. */
    public var items: List<Item>? by _items

    /**
     * Style overrides which can be used to override the widget's default
     * appearance (ex. font size, color, weight, etc.).
     */
    public var styleOverrides: InfoWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ExpressCheckoutCalloutView, 0, 0)
            .apply {
                try {
                    getBorderStyle(
                        widgetName = WIDGET_NAME,
                        borderStyleResId = R.styleable.ExpressCheckoutCalloutView_borderStyle,
                        customBorderColorResId = R.styleable.ExpressCheckoutCalloutView_customBorderColor,
                        customBorderRadiusResId = R.styleable.ExpressCheckoutCalloutView_customBorderRadius,
                    )?.let {
                        _borderStyle.value = it
                    }
                    _colorTheme.value =
                        getCatchColorTheme(R.styleable.ExpressCheckoutCalloutView_colorTheme)
                } finally {
                    recycle()
                }
            }
    }

    @Composable
    override fun Content() {
        ExpressCheckoutCallout(
            price = _price.value ?: 0,
            items = _items.value,
            userCohorts = _userCohorts.value,
            borderStyle = _borderStyle.value,
            colorTheme = _colorTheme.value,
            styleOverrides = _styleOverrides.value,
        )
    }

    internal companion object {
        const val WIDGET_NAME = "ExpressCheckoutCalloutView"
    }
}
