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

/**
 * The CampaignLink widget is designed to be displayed on your order confirmation page if Catch was
 * **not** used as a payment method to offer credits to the consumer if they pay with Catch for
 * their next purchase.
 *
 * The widget displays information about the amount of credits the consumer can claim based on the
 * reward campaign’s name. The widget also acts as a hyperlink, directing consumers to a page where
 * they can claim the credits.
 */
public class CampaignLinkView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _campaignName: MutableState<String?> = mutableStateOf(null)
    private val _borderStyle: MutableState<BorderStyle> = mutableStateOf(BorderStyle.SlightRound)
    private val _theme: MutableState<ThemeVariant?> = mutableStateOf(null)
    private val _styleOverrides: MutableState<ActionWidgetStyle?> = mutableStateOf(null)

    /** The name of a valid and active Catch campaign. */
    public var campaignName: String? by _campaignName

    /**
     * The [BorderStyle] that the widget renders. Defaults to the [BorderStyle.SlightRound] style.
     */
    public var borderStyle: BorderStyle by _borderStyle

    /**
     * The Catch color [ThemeVariant]. If no theme is set, the theme set globally on the
     * [Catch] object will be used, which defaults to [ThemeVariant.Light].
     */
    public var theme: ThemeVariant? by _theme

    /**
     * Style overrides which can be used to override the widget's default
     * appearance (ex. font size, color, weight, etc.).
     */
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
                    _theme.value =
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
                theme = _theme.value,
                styleOverrides = _styleOverrides.value,
            )
        }
    }

    internal companion object {
        const val WIDGET_NAME = "CampaignLinkView"
    }
}
