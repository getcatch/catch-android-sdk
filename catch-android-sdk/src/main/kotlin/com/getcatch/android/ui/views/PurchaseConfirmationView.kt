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
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.getBorderStyle
import com.getcatch.android.utils.getThemeVariant

public class PurchaseConfirmationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _earned: MutableState<Int> = mutableStateOf(0)
    public var earned: Int by _earned

    private val _donation: MutableState<Int> = mutableStateOf(0)
    public var donation: Int by _donation

    private val _borderStyle: MutableState<BorderStyle> = mutableStateOf(BorderStyle.SlightRound)
    public var borderStyle: BorderStyle by _borderStyle

    private val _themeVariant: MutableState<ThemeVariant?> = mutableStateOf(null)
    public var themeVariant: ThemeVariant? by _themeVariant

    private val _styleOverrides: MutableState<ActionWidgetStyle?> = mutableStateOf(null)
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
                    _themeVariant.value =
                        getThemeVariant(R.styleable.PurchaseConfirmationView_themeVariant)
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
            theme = _themeVariant.value,
            styleOverrides = _styleOverrides.value,
        )
    }

    internal companion object {
        const val WIDGET_NAME = "PurchaseConfirmationView"
    }
}
