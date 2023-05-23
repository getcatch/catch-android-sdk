package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.Catch
import com.getcatch.android.R
import com.getcatch.android.models.Item
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.InfoWidgetType
import com.getcatch.android.ui.composables.elements.BenefitText
import com.getcatch.android.ui.composables.elements.CatchText
import com.getcatch.android.ui.composables.elements.EarnRedeemContent
import com.getcatch.android.ui.composables.elements.FillerText
import com.getcatch.android.ui.composables.elements.InfoIcon
import com.getcatch.android.ui.composables.elements.InlineLogo
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.border
import com.getcatch.android.utils.transformAndAppend
import com.getcatch.android.viewmodels.EarnRedeemUiState
import com.getcatch.android.viewmodels.EarnRedeemViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * The `ExpressCheckoutCallout` widget displays similar informational content as the
 * [`Callout`](Callout) widget with additional messaging on where to find Catch in
 * the checkout flow.
 *
 * It is intended to be displayed in merchant checkout flows in which an express checkout option is
 * present. Since Catch can only be selected on the final step of checkout, this messaging is meant
 * to reduce confusion if the consumer intends to pay with Catch but does not see it displayed as an
 * express checkout option. The widget also includes a button to open an informational modal with
 * more details about paying with Catch and with links to visit Catch's marketing website.
 *
 * The `ExpressCheckoutCallout` widget makes use of its `price`, `items`, and `userCohorts`
 * params to calculate rewards the user will earn on the current purchase.
 *
 * @param price The cost in cents that a consumer would pay for the item(s) without redeeming Catch
 * credit.
 *
 * @param items A list of all items included in the order. Used to calculate item-based rewards.
 *
 * @param userCohorts A list of user cohorts that the signed in user qualifies for. Used to
 * calculate user cohort based rewards.
 *
 * @param borderStyle The [`BorderStyle`](BorderStyle) that the widget renders. Defaults to the
 * [`BorderStyle.None`](BorderStyle.None) style.
 *
 * @param theme The Catch color [`ThemeVariant`](ThemeVariant). If no theme is set, the theme set
 * globally on the [`Catch`](Catch) object will be used, which defaults to
 * [`ThemeVariant.Light`](ThemeVariant.Light).
 *
 * @param styleOverrides Style overrides which can be used to override the widget's default
 * appearance (ex. font size, color, weight, etc.).
 */
@Composable
public fun ExpressCheckoutCallout(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    borderStyle: BorderStyle = BorderStyle.None,
    theme: ThemeVariant? = null,
    styleOverrides: InfoWidgetStyle? = null,
) {
    Catch.assertInitialized()
    val viewModelKey = EarnRedeemViewModel.generateKey(
        price = price,
        items = items,
        userCohorts = userCohorts,
    )

    ExpressCheckoutCalloutInternal(
        price = price,
        items = items,
        userCohorts = userCohorts,
        borderStyle = borderStyle,
        theme = theme,
        styleOverrides = styleOverrides,
        viewModel = koinViewModel(key = viewModelKey),
    )
}

@Composable
internal fun ExpressCheckoutCalloutInternal(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    borderStyle: BorderStyle = BorderStyle.None,
    theme: ThemeVariant? = null,
    styleOverrides: InfoWidgetStyle? = null,
    viewModel: EarnRedeemViewModel,
) {
    LaunchedEffect(price, items, userCohorts) {
        viewModel.update(
            price = price, items = items, userCohorts = userCohorts
        )
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CatchTheme(theme) {
        val variant = CatchTheme.variant
        val styles by remember(variant, styleOverrides) {
            mutableStateOf(
                StyleResolver.infoWidgetStyles(
                    theme = variant,
                    instanceOverrides = styleOverrides,
                    infoWidgetType = InfoWidgetType.ExpressCheckoutCallout,
                )
            )
        }
        var containerModifier = Modifier.animateContentSize()
        if (borderStyle !is BorderStyle.None) {
            containerModifier = containerModifier
                .border(borderStyle)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        }
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val condense = maxWidth < 560.dp
            ExpressCheckoutCalloutContent(
                modifier = containerModifier,
                uiState = uiState,
                styles = styles,
                condense = condense,
                price = price,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExpressCheckoutCalloutContent(
    price: Int,
    modifier: Modifier = Modifier,
    uiState: EarnRedeemUiState,
    styles: InfoWidgetStyle.Resolved,
    condense: Boolean
) {
    FlowRow(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
        maxItemsInEachRow = if (condense) 1 else Int.MAX_VALUE
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            EarnRedeemContent(uiState, styles) { reward, summary ->
                BenefitText(reward = reward, styles = styles, price = price, summary = summary)
                Spacer(modifier = Modifier.width(3.dp))
                FillerText(text = stringResource(R.string.with), styles = styles)
            }
            Spacer(modifier = Modifier.width(2.dp))
            InlineLogo(
                fontSize = styles.widgetTextStyle.fontSize,
                widgetType = InfoWidgetType.ExpressCheckoutCallout
            )
        }
        if (!condense) {
            Spacer(modifier = Modifier.width(3.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            val textTransform = styles.widgetTextStyle.textTransform
            CatchText(
                text = buildAnnotatedString {
                    if (!condense) {
                        transformAndAppend(
                            textTransform,
                            stringResource(id = R.string.em_dash_prefix)
                        )
                    }
                    transformAndAppend(textTransform, stringResource(id = R.string.find_us_at_the))
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W700)) {
                        transformAndAppend(
                            textTransform,
                            stringResource(id = R.string.payment_step)
                        )
                    }
                },
                style = styles.composeTextStyle,
                overflow = TextOverflow.Visible,
                maxLines = 1,
                softWrap = false,
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
