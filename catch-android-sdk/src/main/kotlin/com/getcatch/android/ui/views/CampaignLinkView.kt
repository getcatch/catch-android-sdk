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
import com.getcatch.android.ui.composables.CampaignLink
import com.getcatch.android.ui.styles.ActionWidgetStyle
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.getBorderStyle
import com.getcatch.android.utils.getCampaignName
import com.getcatch.android.utils.getThemeVariant

public class CampaignLinkView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _campaignName: MutableState<String?> = mutableStateOf(null)
    public var campaignName: String? by _campaignName

    private val _borderStyle: MutableState<BorderStyle> = mutableStateOf(BorderStyle.SlightRound)
    public var borderStyle: BorderStyle by _borderStyle

    private val _themeVariant: MutableState<ThemeVariant?> = mutableStateOf(null)
    public var themeVariant: ThemeVariant? by _themeVariant

    private val _styleOverrides: MutableState<ActionWidgetStyle?> = mutableStateOf(null)
    public var styleOverrides: ActionWidgetStyle? by _styleOverrides

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CampaignLinkView, 0, 0)
            .apply {
                try {
                    _campaignName.value = getCampaignName()
                    getBorderStyle(
                        widgetName = WIDGET_NAME,
                        borderStyleResId = R.styleable.CampaignLinkView_borderStyle,
                        customBorderColorResId = R.styleable.CampaignLinkView_customBorderColor,
                        customBorderRadiusResId = R.styleable.CampaignLinkView_customBorderRadius,
                    )?.let {
                        _borderStyle.value = it
                    }
                    _themeVariant.value =
                        getThemeVariant(R.styleable.CampaignLinkView_themeVariant)
                } finally {
                    recycle()
                }
            }
    }

    @Composable
    override fun Content() {
        _campaignName.value?.let {
            CampaignLink(
                campaignName = it,
                borderStyle = _borderStyle.value,
                theme = _themeVariant.value,
                styleOverrides = _styleOverrides.value,
            )
        }
    }

    internal companion object {
        const val WIDGET_NAME = "CampaignLinkView"
    }
}
