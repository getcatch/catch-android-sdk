package com.getcatch.android.ui.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.getcatch.android.R
import com.getcatch.android.models.Item
import com.getcatch.android.ui.BorderStyle
import com.getcatch.android.ui.InfoWidgetType
import com.getcatch.android.ui.PaymentMethodVariant
import com.getcatch.android.ui.composables.elements.BenefitText
import com.getcatch.android.ui.composables.elements.EarnRedeemContent
import com.getcatch.android.ui.composables.elements.FillerText
import com.getcatch.android.ui.composables.elements.InfoIcon
import com.getcatch.android.ui.composables.elements.InlineLogo
import com.getcatch.android.ui.styles.InfoWidgetStyle
import com.getcatch.android.ui.styles.StyleResolver
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariantOption
import com.getcatch.android.utils.Constants
import com.getcatch.android.viewmodels.EarnRedeemUiState
import com.getcatch.android.viewmodels.EarnRedeemViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun PaymentMethod(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    disabled: Boolean = false,
    variant: PaymentMethodVariant = PaymentMethodVariant.Standard,
    borderStyle: BorderStyle? = null,
    theme: ThemeVariantOption? = null,
    styleOverrides: InfoWidgetStyle? = null,
) {
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
        borderStyle = borderStyle,
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
    borderStyle: BorderStyle? = null,
    theme: ThemeVariantOption? = null,
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
        val styles by remember(variant, styleOverrides) {
            mutableStateOf(
                StyleResolver.infoWidgetStyles(
                    theme = themeVariant,
                    instanceOverrides = styleOverrides,
                    infoWidgetType = InfoWidgetType.PaymentMethod(variant)
                )
            )
        }

        var rowModifier = Modifier
            .wrapContentWidth()
            .animateContentSize()
        if (borderStyle != null) {
            val borderColor = when (borderStyle) {
                is BorderStyle.Custom -> borderStyle.color
                else -> CatchTheme.colors.border
            }
            rowModifier = rowModifier
                .border(1.dp, borderColor, borderStyle.shape)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        }

        Row(
            modifier = rowModifier,
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
            EarnRedeemContent(uiState, styles) { reward ->
                FlowRow(verticalAlignment = Alignment.CenterVertically) {
                    if (variant !is PaymentMethodVariant.LogoCompact) {
                        FillerText(
                            text = stringResource(R.string.pay_by_bank),
                            styles = styles,
                            modifier = disabledModifier
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BenefitText(
                            reward = reward,
                            styles = styles,
                            modifier = disabledModifier,
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        InfoIcon(styles.composeTextStyle)
                    }
                }
            }
            if (uiState is EarnRedeemUiState.Loading) {
                Spacer(modifier = Modifier.width(2.dp))
                InfoIcon(styles.composeTextStyle)
            }
        }
    }
}
