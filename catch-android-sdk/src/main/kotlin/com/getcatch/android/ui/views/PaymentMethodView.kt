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
import com.getcatch.android.ui.PaymentMethodVariant
import com.getcatch.android.ui.composables.PaymentMethod
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.utils.getCatchColorTheme
import com.getcatch.android.utils.getPaymentMethodVariant

/**
 * The `PaymentMethod` widget displays similar messaging and informational content as the
 * [`Callout`](CalloutView) widget, but is designed specifically to be displayed in checkout UI
 * where a consumer may select Catch as their payment method.
 *
 * The widget may be placed in a "disabled" state when the cart contains items that cannot be
 * purchased with Catch (e.g. gift cards). The disable state grays out the contents of the widget
 * and provides a small tooltip that explains why Catch cannot be selected.
 *
 * The `PaymentMethod` widget makes use of its `price`, `items`, and `userCohorts` properties
 * to calculate rewards the user will earn on the current purchase.
 */
public class PaymentMethodView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _price = mutableStateOf<Int?>(null)
    private val _items = mutableStateOf<List<Item>?>(null)
    private val _userCohorts = mutableStateOf<List<String>?>(null)
    private val _variant = mutableStateOf<PaymentMethodVariant>(PaymentMethodVariant.Standard)
    private val _disabled = mutableStateOf(false)
    private val _colorTheme = mutableStateOf<CatchColorTheme?>(null)
    private val _styleOverrides = mutableStateOf<InfoWidgetStyle?>(null)

    /**
     * The cost in cents that a consumer would pay for the item(s)
     * without redeeming Catch credit.
     */
    public var price: Int? by _price

    /** A list of all items included in the order. Used to calculate item-based rewards. */
    public var items: List<Item>? by _items

    /**
     * A list of user cohorts that the signed in user qualifies for.
     * Used to calculate cohort-based rewards.
     */
    public var userCohorts: List<String>? by _userCohorts

    /** Whether or not the widget is in a disabled state. */
    public var disabled: Boolean by _disabled

    /**
     * The `PaymentMethod` has several variants that allow you to customize what content
     * is rendered in the widget. The [`PaymentMethodVariant`](PaymentMethodVariant) options are:
     *  - [`Standard`](PaymentMethodVariant.Standard) - displays the logo, filler text, and reward text
     *  - [`Compact`](PaymentMethodVariant.Compact) - displays the filler text and reward text
     *  - [`LogoCompact`](PaymentMethodVariant.LogoCompact) - displays the logo and reward text
     */
    public var variant: PaymentMethodVariant by _variant

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
        context.theme.obtainStyledAttributes(attrs, R.styleable.PaymentMethodView, 0, 0).apply {
            try {
                getPaymentMethodVariant()?.let { _variant.value = it }
                _colorTheme.value = getCatchColorTheme(R.styleable.PaymentMethodView_colorTheme)
            } finally {
                recycle()
            }
        }
    }

    @Composable
    override fun Content() {
        PaymentMethod(
            price = _price.value ?: 0,
            items = _items.value,
            userCohorts = _userCohorts.value,
            disabled = _disabled.value,
            variant = _variant.value,
            colorTheme = _colorTheme.value,
            styleOverrides = _styleOverrides.value,
        )
    }
}
