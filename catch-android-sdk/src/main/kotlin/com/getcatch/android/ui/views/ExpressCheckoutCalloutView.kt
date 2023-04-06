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
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.getBorderStyle
import com.getcatch.android.utils.getThemeVariant

public class ExpressCheckoutCalloutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _price = mutableStateOf<Int?>(null)
    public var price: Int? by _price

    private val _borderStyle = mutableStateOf<BorderStyle?>(null)
    public var borderStyle: BorderStyle? by _borderStyle

    private val _themeVariant = mutableStateOf<ThemeVariant?>(null)
    public var themeVariant: ThemeVariant? by _themeVariant

    private val _items = mutableStateOf<List<Item>?>(null)
    public var items: List<Item>? by _items

    private val _userCohorts = mutableStateOf<List<String>?>(null)
    public var userCohorts: List<String>? by _userCohorts

    private val _styleOverrides = mutableStateOf<InfoWidgetStyle?>(null)
    public var styleOverrides: InfoWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ExpressCheckoutCalloutView, 0, 0).apply {
            try {
                _borderStyle.value = getBorderStyle(
                    widgetName = WIDGET_NAME,
                    borderStyleResId = R.styleable.ExpressCheckoutCalloutView_borderStyle,
                    customBorderColorResId = R.styleable.ExpressCheckoutCalloutView_customBorderColor,
                    customBorderRadiusResId = R.styleable.ExpressCheckoutCalloutView_customBorderRadius,
                )
                _themeVariant.value = getThemeVariant(R.styleable.ExpressCheckoutCalloutView_themeVariant)
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
            theme = _themeVariant.value,
            styleOverrides = _styleOverrides.value,
        )
    }

    internal companion object {
        const val WIDGET_NAME = "ExpressCheckoutCalloutView"
    }
}
