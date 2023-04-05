package com.getcatch.android.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import com.getcatch.android.R
import com.getcatch.android.composables.Callout
import com.getcatch.android.models.Item
import com.getcatch.android.styling.InfoWidgetStyle
import com.getcatch.android.theming.CalloutBorderStyle
import com.getcatch.android.theming.ThemeVariant
import com.getcatch.android.utils.getCalloutBorderStyle
import com.getcatch.android.utils.getHasOrPrefix
import com.getcatch.android.utils.getThemeVariant

public class CalloutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _price = mutableStateOf<Int?>(null)
    public var price: Int? by _price

    private val _hasOrPrefix = mutableStateOf(false)
    public var hasOrPrefix: Boolean by _hasOrPrefix

    private val _borderStyle = mutableStateOf<CalloutBorderStyle?>(null)
    public var borderStyle: CalloutBorderStyle? by _borderStyle

    private val _themeVariant = mutableStateOf<ThemeVariant?>(null)
    public var themeVariant: ThemeVariant? by _themeVariant

    private val _items = mutableStateOf<List<Item>?>(null)
    public var items: List<Item>? by _items

    private val _userCohorts = mutableStateOf<List<String>?>(null)
    public var userCohorts: List<String>? by _userCohorts

    private val _styleOverrides = mutableStateOf<InfoWidgetStyle?>(null)
    public var styleOverrides: InfoWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CalloutView, 0, 0).apply {
            try {
                _hasOrPrefix.value = getHasOrPrefix()
                _borderStyle.value = getCalloutBorderStyle()
                _themeVariant.value = getThemeVariant(R.styleable.CalloutView_themeVariant)
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
            theme = _themeVariant.value,
            items = _items.value,
            userCohorts = _userCohorts.value,
            styleOverrides = _styleOverrides.value,
        )
    }
}
