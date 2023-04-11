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
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.getPaymentMethodVariant
import com.getcatch.android.utils.getThemeVariant

public class PaymentMethodView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _price = mutableStateOf<Int?>(null)
    public var price: Int? by _price

    private val _variant = mutableStateOf<PaymentMethodVariant>(PaymentMethodVariant.Standard)
    public var variant: PaymentMethodVariant by _variant

    private val _disabled = mutableStateOf(false)
    public var disabled: Boolean by _disabled

    private val _themeVariant = mutableStateOf<ThemeVariant?>(null)
    public var themeVariant: ThemeVariant? by _themeVariant

    private val _items = mutableStateOf<List<Item>?>(null)
    public var items: List<Item>? by _items

    private val _userCohorts = mutableStateOf<List<String>?>(null)
    public var userCohorts: List<String>? by _userCohorts

    private val _styleOverrides = mutableStateOf<InfoWidgetStyle?>(null)
    public var styleOverrides: InfoWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.PaymentMethodView, 0, 0).apply {
            try {
                getPaymentMethodVariant()?.let { _variant.value = it }
                _themeVariant.value = getThemeVariant(R.styleable.PaymentMethodView_themeVariant)
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
            theme = _themeVariant.value,
            styleOverrides = _styleOverrides.value,
        )
    }
}
