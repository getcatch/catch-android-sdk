package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.Catch
import com.getcatch.android.R
import com.getcatch.android.models.Item
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.InfoWidgetType
import com.getcatch.android.ui.composables.elements.BenefitText
import com.getcatch.android.ui.composables.elements.EarnRedeemContent
import com.getcatch.android.ui.composables.elements.FillerText
import com.getcatch.android.ui.composables.elements.InfoIcon
import com.getcatch.android.ui.composables.elements.InlineLogo
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.utils.border
import com.getcatch.android.viewmodels.EarnRedeemUiState
import com.getcatch.android.viewmodels.EarnRedeemViewModel
import org.koin.androidx.compose.koinViewModel

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
 * The `Callout` widget makes use of its `price`, `items`, and `userCohorts` params to calculate
 * rewards the user will earn on the current item (if implemented on product detail page) or
 * on the current order (if implemented in the cart or during the checkout flow).
 *
 * @param price The cost in cents that a consumer would pay for the item(s) without redeeming Catch
 * credit. If not set, the widgets will display the rewards rate (e.g. “Earn 10% credit”) rather
 * than a specific rewards value (e.g., "Earn $24.00 credit"). If provided, the price must be a
 * positive number. A negative price will be treated as if the price is not set at all.
 *
 * @param items A list of items included in the order (i.e. on PDP, this would be the single item
 * displayed on the page. On the cart/checkout pages, this would be a list of all items included in the order).
 * This is used to calculate SKU-based rewards.
 *
 * @param userCohorts A list of user cohorts that the signed in user qualifies for. Used to calculate
 * user cohort based rewards.
 *
 * @param hasOrPrefix If or-prefix is set, the word "or" is prepended into the displayed messaging
 * (e.g. "or earn $23.00 credit" instead of "Earn $23.00 credit"). Intended to be used when the
 * callout is found below other payment method callout widgets.
 *
 * @param borderStyle The [`CalloutBorderStyle`](CalloutBorderStyle) that the widget renders.
 * Defaults to the [`CalloutBorderStyle.None`](CalloutBorderStyle.None) style.
 *
 * @param colorTheme The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set
 * globally on the [`Catch`](Catch) object will be used, which defaults to
 * [`CatchColorTheme.Light`](CatchColorTheme.Light).
 *
 * @param styleOverrides Style overrides which can be used to override the widget's default
 * appearance (ex. font size, color, weight, etc.).
 */
@Composable
public fun Callout(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    hasOrPrefix: Boolean = false,
    borderStyle: CalloutBorderStyle = CalloutBorderStyle.None,
    colorTheme: CatchColorTheme? = null,
    styleOverrides: InfoWidgetStyle? = null,
) {
    Catch.assertInitialized()
    val viewModelKey = EarnRedeemViewModel.generateKey(
        price = price,
        items = items,
        userCohorts = userCohorts,
    )

    CalloutInternal(
        price = price,
        items = items,
        userCohorts = userCohorts,
        hasOrPrefix = hasOrPrefix,
        borderStyle = borderStyle,
        colorTheme = colorTheme,
        styleOverrides = styleOverrides,
        viewModel = koinViewModel(key = viewModelKey)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CalloutInternal(
    price: Int,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    hasOrPrefix: Boolean = false,
    borderStyle: CalloutBorderStyle = CalloutBorderStyle.None,
    colorTheme: CatchColorTheme? = null,
    styleOverrides: InfoWidgetStyle? = null,
    viewModel: EarnRedeemViewModel,
) {
    LaunchedEffect(price, items, userCohorts) {
        viewModel.update(
            price = price, items = items, userCohorts = userCohorts
        )
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatchTheme(colorTheme) {
        val themeColors = CatchTheme.colors
        val styles by remember(CatchTheme.colorTheme, styleOverrides) {
            mutableStateOf(
                StyleResolver.infoWidgetStyles(
                    themeColors = themeColors,
                    instanceOverrides = styleOverrides,
                    infoWidgetType = InfoWidgetType.Callout
                )
            )
        }

        var rowModifier = Modifier.animateContentSize()

        if (borderStyle !is CalloutBorderStyle.None) {
            rowModifier = rowModifier
                .border(borderStyle)
                .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
        }

        FlowRow(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            EarnRedeemContent(uiState, styles) { reward, summary ->
                if (hasOrPrefix) {
                    FillerText(text = stringResource(R.string.or_prefix), styles = styles)
                    Spacer(modifier = Modifier.width(3.dp))
                }
                BenefitText(
                    reward = reward,
                    styles = styles,
                    capitalize = !hasOrPrefix,
                    price = price,
                    summary = summary
                )
                Spacer(modifier = Modifier.width(3.dp))
                FillerText(text = stringResource(R.string.by_paying_with), styles = styles)
            }

            Spacer(modifier = Modifier.width(2.dp))
            InlineLogo(
                fontSize = styles.widgetTextStyle.fontSize,
                widgetType = InfoWidgetType.Callout,
            )
            Spacer(modifier = Modifier.width(2.dp))
            InfoIcon(
                styles.composeTextStyle,
                price = price,
                rewardsSummary = (uiState as? EarnRedeemUiState.Success)?.summary
            )
        }
    }
}
