package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.ui.activities.tofu.TOFUActivity
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.typography.CatchTextStyles

private const val ANNOTATION_TAG = "DISABLED_TOOLTIP_LEARN_MORE_TAG"
private const val ANNOTATION_VALUE = "DISABLED_TOOLTIP_LEARN_MORE"

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun DisabledTooltipContent(
    textStyle: TextStyle,
    onDismissRequested: () -> Unit,
    price: Int?,
    rewardsSummary: EarnedRewardsSummary?,
) {
    val context = LocalContext.current
    val ctaBody = stringResource(id = R.string.disabled_tooltip_body)
    val ctaText = stringResource(id = R.string.disabled_tooltip_cta)
    val annotatedStringBody = buildAnnotatedString {
        append(ctaBody)
        append("\n")
        withAnnotation(ANNOTATION_TAG, ANNOTATION_VALUE) {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.W700,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(ctaText)
            }
        }
    }

    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClickableText(
            text = annotatedStringBody,
            style = textStyle,
            modifier = Modifier.weight(1f),
            onClick = { offset ->
                annotatedStringBody.getStringAnnotations(start = offset, end = offset).firstOrNull()
                    ?.let {
                        TOFUActivity.open(context, price, rewardsSummary)
                    }
            }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .clickable { onDismissRequested() }
        )
    }
}

@Preview
@Composable
internal fun PreviewDisabledTooltipContent() {
    CatchTheme {
        Box(
            Modifier
                .background(color = Color.White)
                .width(300.dp)
        ) {
            DisabledTooltipContent(
                textStyle = CatchTextStyles.bodyRegular,
                onDismissRequested = {},
                price = null,
                rewardsSummary = null
            )
        }
    }
}
