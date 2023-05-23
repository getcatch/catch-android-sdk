package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.Catch
import com.getcatch.android.R
import com.getcatch.android.models.Item
import com.getcatch.android.ui.InfoWidgetType
import com.getcatch.android.ui.PaymentMethodVariant
import com.getcatch.android.ui.composables.elements.BenefitText
import com.getcatch.android.ui.composables.elements.DisabledTooltipContent
import com.getcatch.android.ui.composables.elements.EarnRedeemContent
import com.getcatch.android.ui.composables.elements.FillerText
import com.getcatch.android.ui.composables.elements.InfoIcon
import com.getcatch.android.ui.composables.elements.InlineLogo
import com.getcatch.android.ui.composables.elements.WidgetTooltip
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.Constants
import com.getcatch.android.viewmodels.EarnRedeemUiState
import com.getcatch.android.viewmodels.EarnRedeemViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * The `PaymentMethod` widget displays similar messaging and informational content as the
 * [`Callout`](Callout) but is designed specifically to be displayed in checkout UI where a consumer
 * may select Catch as their payment method.
 *
 * The widget may be placed in a "disabled" state when the cart contains items that cannot be
 * purchased with Catch (e.g. gift cards). The disable state grays out the contents of the widget
 * and provides a small tooltip that explains why Catch cannot be selected.
 *
 * The `PaymentMethod` widget makes use of its `price`, `items`, and `userCohorts` params to
 * calculate rewards the user will earn on the current purchase.
 *
 * @param price The cost in cents that a consumer would pay for the item(s)
 * without redeeming Catch credit.
 *
 * @param items A list of all items included in the order. Used to calculate item-based rewards.
 *
 * @param userCohorts A list of user cohorts that the signed in user qualifies for.
 * Used to calculate cohort-based rewards.
 *
 * @param disabled Whether or not the widget is in a disabled state.
 *
 * @param variant The `PaymentMethod` has several variants that allow you to customize what content
 * is rendered in the widget. The [`PaymentMethodVariant`](PaymentMethodVariant) options are:
 *  - [`Standard`](PaymentMethodVariant.Standard) - displays the logo, filler text, and reward text
 *  - [`Compact`](PaymentMethodVariant.Compact) - displays the filler text and reward text
 *  - [`LogoCompact`](PaymentMethodVariant.LogoCompact) - displays the logo and reward text
 *
 * @param theme The Catch color [`ThemeVariant`](ThemeVariant). If no theme is set, the theme set
 * globally on the [`Catch`](Catch) object will be used, which defaults to
 * [`ThemeVariant.Light`](ThemeVariant.Light).
 *
 * @param styleOverrides Style overrides which can be used to override the widget's default
 * appearance (ex. font size, color, weight, etc.).
 */
@Composable
public fun PaymentMethod(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    disabled: Boolean = false,
    variant: PaymentMethodVariant = PaymentMethodVariant.Standard,
    theme: ThemeVariant? = null,
    styleOverrides: InfoWidgetStyle? = null,
) {
    Catch.assertInitialized()
    val viewModelKey = EarnRedeemViewModel.generateKey(
        price = price,
        items = items,
        userCohorts = userCohorts,
        disabled = disabled,
    )

    PaymentMethodInternal(
        price = price,
        items = items,
        userCohorts = userCohorts,
        disabled = disabled,
        variant = variant,
        theme = theme,
        styleOverrides = styleOverrides,
        viewModel = koinViewModel(key = viewModelKey),
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun PaymentMethodInternal(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    disabled: Boolean = false,
    variant: PaymentMethodVariant = PaymentMethodVariant.Standard,
    theme: ThemeVariant? = null,
    styleOverrides: InfoWidgetStyle? = null,
    viewModel: EarnRedeemViewModel,
) {
    LaunchedEffect(price, items, userCohorts, disabled) {
        viewModel.update(
            price = if (disabled) 0 else price, items = items, userCohorts = userCohorts
        )
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CatchTheme(theme) {
        val themeVariant = CatchTheme.variant
        val styles by remember(variant, themeVariant, styleOverrides) {
            mutableStateOf(
                StyleResolver.infoWidgetStyles(
                    theme = themeVariant,
                    instanceOverrides = styleOverrides,
                    infoWidgetType = InfoWidgetType.PaymentMethod(variant)
                )
            )
        }

        var showTooltip by remember { mutableStateOf(false) }
        var widgetWidth by remember { mutableStateOf(Dp.Unspecified) }
        val infoIconOnClickOverride = if (disabled) ({ showTooltip = true }) else null
        val pixelDensity = LocalDensity.current

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .animateContentSize()
                .onGloballyPositioned { coordinates ->
                    widgetWidth = with(pixelDensity) { coordinates.size.width.toDp() }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val disabledModifier =
                if (disabled) Modifier.alpha(Constants.DISABLED_ALPHA)
                else Modifier
            if (variant !is PaymentMethodVariant.Compact) {
                InlineLogo(
                    fontSize = styles.widgetTextStyle.fontSize,
                    widgetType = InfoWidgetType.PaymentMethod(variant),
                    modifier = disabledModifier,
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            EarnRedeemContent(uiState, styles) { reward, summary ->
                FlowRow(
                    modifier = Modifier.wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (variant !is PaymentMethodVariant.LogoCompact) {
                        FillerText(
                            text = stringResource(R.string.pay_by_bank),
                            styles = styles,
                            modifier = disabledModifier
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BenefitText(
                            reward = reward,
                            styles = styles,
                            modifier = disabledModifier,
                            price = price,
                            summary = summary,
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        InfoIcon(
                            styles.composeTextStyle,
                            price = price,
                            rewardsSummary = summary,
                            onClickOverride = infoIconOnClickOverride
                        )
                    }
                }
            }
            if (uiState is EarnRedeemUiState.Loading) {
                Spacer(modifier = Modifier.width(2.dp))
                InfoIcon(
                    styles.composeTextStyle,
                    price = price,
                    onClickOverride = infoIconOnClickOverride
                )
            }
            if (showTooltip && widgetWidth != Dp.Unspecified) {
                WidgetTooltip(
                    widgetWidth = widgetWidth,
                    onDismissRequested = { showTooltip = false }) {
                    DisabledTooltipContent(
                        textStyle = styles.composeTextStyle,
                        onDismissRequested = { showTooltip = false },
                        price = price,
                        rewardsSummary = (uiState as? EarnRedeemUiState.Success)?.summary
                    )
                }
            }
        }
    }
}
