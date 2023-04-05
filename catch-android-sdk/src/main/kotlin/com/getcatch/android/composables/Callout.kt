package com.getcatch.android.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.composables.elements.BenefitText
import com.getcatch.android.composables.elements.InfoIcon
import com.getcatch.android.models.Item
import com.getcatch.android.styling.InfoWidgetStyle
import com.getcatch.android.styling.StyleResolver
import com.getcatch.android.theming.CalloutBorderStyle
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.ThemeVariantOption
import com.getcatch.android.viewmodels.EarnRedeemViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
private fun CalloutInternal(
    price: Int,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    hasOrPrefix: Boolean = false,
    borderStyle: CalloutBorderStyle? = null,
    theme: ThemeVariantOption? = null,
    styleOverrides: InfoWidgetStyle? = null,
    viewModel: EarnRedeemViewModel,
) {
    LaunchedEffect(price) {
        viewModel.init(
            price = price,
            items = items,
            userCohorts = userCohorts
        )
    }
    val uiState by viewModel.uiState.collectAsState()

    CatchTheme(theme) {
        val variant = CatchTheme.variant
        val styles by remember {
            mutableStateOf(
                StyleResolver.calloutStyles(
                    variant,
                    styleOverrides
                )
            )
        }

        var rowModifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .animateContentSize()

        if (borderStyle != null) {
            val borderColor = when (borderStyle) {
                is CalloutBorderStyle.Custom -> borderStyle.color
                else -> CatchTheme.colors.border
            }
            rowModifier =
                rowModifier
                    .border(1.dp, borderColor, borderStyle.shape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
        }

        Row(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BenefitText(
                uiState = uiState,
                capitalize = !hasOrPrefix,
                prefix = if (hasOrPrefix) stringResource(R.string.or_prefix) else null,
                suffix = stringResource(
                    R.string.by_paying_with
                )
            )
            Spacer(modifier = Modifier.width(2.dp))
            Image(
                painter = painterResource(id = CatchTheme.variant.logoResId),
                contentDescription = stringResource(
                    id = R.string.content_description_catch_logo
                ),
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(18.dp),
            )
            Spacer(modifier = Modifier.width(2.dp))
            InfoIcon(styles.composeTextStyle)
        }
    }
}

@Composable
public fun Callout(
    price: Int = 0,
    items: List<Item>? = null,
    userCohorts: List<String>? = null,
    hasOrPrefix: Boolean = false,
    borderStyle: CalloutBorderStyle? = null,
    theme: ThemeVariantOption? = null,
    styleOverrides: InfoWidgetStyle? = null,
) {
    val viewModelKey by remember {
        mutableStateOf(
            EarnRedeemViewModel.generateKey(
                price = price,
                items = items,
                userCohorts = userCohorts,
            )
        )
    }
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

@Preview(name = "TOFUWidget")
@Composable
public fun PreviewTOFUWidget() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Callout()
    }
}
