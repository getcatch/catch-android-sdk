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
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.border
import com.getcatch.android.viewmodels.EarnRedeemUiState
import com.getcatch.android.viewmodels.EarnRedeemViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun Callout(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    hasOrPrefix: Boolean = false,
    borderStyle: CalloutBorderStyle = CalloutBorderStyle.None,
    theme: ThemeVariant? = null,
    styleOverrides: InfoWidgetStyle? = null,
) {
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
        theme = theme,
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
