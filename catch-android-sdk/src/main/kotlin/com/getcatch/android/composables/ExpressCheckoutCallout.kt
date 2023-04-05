package com.getcatch.android.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.composables.elements.EarnRedeemText
import com.getcatch.android.composables.elements.InfoIcon
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.LocalThemeVariant
import com.getcatch.android.ui.CalloutBorderStyle
import com.getcatch.android.ui.typography.CatchTextStyles

@Composable
public fun ExpressCheckoutCallout(borderStyle: CalloutBorderStyle? = null) {
    CatchTheme {
        var containerModifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .animateContentSize()
        if (borderStyle != null) {
            containerModifier =
                containerModifier
                    .border(1.dp, CatchTheme.colors.border, borderStyle.shape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
        }
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            ExpressCheckoutCalloutContainer(modifier = containerModifier)
        }
    }
}

@Composable
private fun BoxWithConstraintsScope.ExpressCheckoutCalloutContainer(modifier: Modifier) {
    if (maxWidth < 560.dp) {
        Column(modifier = modifier.align(Alignment.Center)) {
            ExpressCheckoutCalloutContent(showDash = false)
        }
    } else {
        Row(modifier = modifier.align(Alignment.Center)) {
            ExpressCheckoutCalloutContent(showDash = true)
        }
    }
}

@Composable
private fun ExpressCheckoutCalloutContent(showDash: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        EarnRedeemText(
            suffix = stringResource(R.string.with),
            textStyle = CatchTextStyles.bodyRegular
        )
        Spacer(modifier = Modifier.width(2.dp))
        Image(
            painter = painterResource(id = LocalThemeVariant.current.logoResId),
            contentDescription = stringResource(
                id = R.string.content_description_catch_logo
            ),
            modifier = Modifier.height(21.dp),
        )
    }
    if (showDash) {
        Spacer(modifier = Modifier.width(3.dp))
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = buildAnnotatedString {
                if (showDash) {
                    append(stringResource(id = R.string.em_dash_prefix))
                }
                append(stringResource(id = R.string.find_us_at_the))
                withStyle(style = SpanStyle(fontWeight = FontWeight.W700)) {
                    append(stringResource(id = R.string.payment_step))
                }
            },
            style = CatchTextStyles.bodyRegular
        )
        Spacer(modifier = Modifier.width(2.dp))
        InfoIcon()
    }
}
