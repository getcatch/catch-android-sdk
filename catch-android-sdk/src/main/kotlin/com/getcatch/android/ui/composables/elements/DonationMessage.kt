package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.models.DonationRecipient
import com.getcatch.android.ui.theming.color.CatchColors
import com.getcatch.android.ui.typography.CatchTextStyles
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.launchUrlIntent

@OptIn(ExperimentalTextApi::class)
@Composable
internal fun DonationMessage(donation: Int, merchantName: String, recipient: DonationRecipient) {
    val bodyColor = Color(CatchColors.GREY_6)
    val linkSpanStyle = SpanStyle(
        color = bodyColor,
        fontSize = CatchTextStyles.linkSmall.fontSize,
        fontWeight = CatchTextStyles.linkSmall.fontWeight,
        textDecoration = CatchTextStyles.linkSmall.textDecoration,
    )
    val annotatedBody = buildAnnotatedString {
        append(stringResource(id = R.string.matched_your_contribution, merchantName))
        withAnnotation(UrlAnnotation(recipient.url)) {
            withStyle(linkSpanStyle) {
                append(recipient.name)
            }
        }
        append(". ")
        append(stringResource(id = R.string.thanks_for_pitching_in))
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color(CatchColors.PURPLE_1))
            .border(1.dp, Color(CatchColors.PURPLE_2), RoundedCornerShape(8.dp))
            .padding(16.dp)
            .widthIn(max = 327.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_donate),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            CatchText(
                stringResource(R.string.donation_message_header, donation.centsToDollarsString()),
                style = CatchTextStyles.h3,
                color = Color(CatchColors.GREY_7)
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        CatchClickableText(
            text = annotatedBody,
            style = CatchTextStyles.bodySmall.copy(color = bodyColor),
            onClick = { offset ->
                annotatedBody.getUrlAnnotations(start = offset, end = offset).firstOrNull()?.let {
                    launchUrlIntent(context, it.item.url)
                }
            }
        )
    }
}

@Preview
@Composable
internal fun PreviewDonationMessage() {
    val recipient = DonationRecipient(name = "Sawyer Dog Fund", url = "https://getcatch.com")
    DonationMessage(
        donation = 50,
        merchantName = "Merch by Catch",
        recipient = recipient
    )
}
