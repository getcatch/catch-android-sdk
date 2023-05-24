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
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.composables.Callout
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.utils.getCalloutBorderStyle
import com.getcatch.android.utils.getCatchColorTheme
import com.getcatch.android.utils.getHasOrPrefix

/**
 * The `Callout` widget shows consumers how much Catch credit they could earn or redeem
 * based on the price of the item(s) they're considering (e.g. when viewing a product detail
 * page or their cart).
 *
 * The widget includes a trigger that, when clicked, opens a modal
 * which displays more detailed informational content about paying with Catch and earning
 * rewards on the merchant's site. The widget automatically recognizes consumers who are
 * currently signed in to Catch, and tailors the messaging to them if they have rewards that
 * are available to redeem with the merchant.
 *
 * The `Callout` widget makes use of its `price`, `items`, and `userCohorts` properties to calculate
 * rewards the user will earn on the current item (if implemented on product detail page) or
 * on the current order (if implemented in the cart or during the checkout flow).
 */
public class CalloutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _price = mutableStateOf<Int?>(null)
    private val _items = mutableStateOf<List<Item>?>(null)
    private val _userCohorts = mutableStateOf<List<String>?>(null)
    private val _hasOrPrefix = mutableStateOf(false)
    private val _borderStyle = mutableStateOf<CalloutBorderStyle>(CalloutBorderStyle.None)
    private val _colorTheme = mutableStateOf<CatchColorTheme?>(null)
    private val _styleOverrides = mutableStateOf<InfoWidgetStyle?>(null)

    /**
     * The cost in cents that a consumer would pay for the item(s) without
     * redeeming Catch credit. If not set, the widgets will display the rewards rate (e.g. “Earn 10% credit”)
     * rather than a specific rewards value (e.g., "Earn $24.00 credit"). If provided, the price must be a
     * positive number. A negative price will be treated as if the price is not set at all.
     */
    public var price: Int? by _price

    /**
     * A list of items included in the order (i.e. on PDP, this would be the single item
     * displayed on the page. On the cart/checkout pages, this would be a list of all items included in the order).
     * This is used to calculate SKU-based rewards.
     */
    public var items: List<Item>? by _items

    /**
     * A list of user cohorts that the signed in user qualifies for. Used to calculate
     * user cohort based rewards.
     */
    public var userCohorts: List<String>? by _userCohorts

    /**
     * If or-prefix is set, the word "or" is prepended into the displayed messaging
     * (e.g. "or earn $23.00 credit" instead of "Earn $23.00 credit"). Intended to be used when the
     * callout is found below other payment method callout widgets.
     */
    public var hasOrPrefix: Boolean by _hasOrPrefix

    /**
     * The [`CalloutBorderStyle`](CalloutBorderStyle) that the widget renders.
     * Defaults to the [`CalloutBorderStyle.None`](CalloutBorderStyle.None) style.
     */
    public var borderStyle: CalloutBorderStyle by _borderStyle

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
    public var styleOverrides: InfoWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CalloutView, 0, 0).apply {
            try {
                _hasOrPrefix.value = getHasOrPrefix()
                getCalloutBorderStyle()?.let { _borderStyle.value = it }
                _colorTheme.value = getCatchColorTheme(R.styleable.CalloutView_colorTheme)
            } finally {
                recycle()
            }
        }
    }

    @Composable
    override fun Content() {
        Callout(
            price = _price.value ?: 0,
            hasOrPrefix = _hasOrPrefix.value,
            borderStyle = _borderStyle.value,
            colorTheme = _colorTheme.value,
            items = _items.value,
            userCohorts = _userCohorts.value,
            styleOverrides = _styleOverrides.value,
        )
    }
}
