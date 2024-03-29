package com.getcatch.android.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.getcatch.android.R
import com.getcatch.android.ui.composables.elements.CatchText
import com.getcatch.android.ui.theming.CatchTheme
import com.getcatch.android.ui.theming.color.CatchColors
import com.getcatch.android.ui.typography.CatchTextStyles
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.format
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

@Composable
internal fun RewardCard(
    rewardsAmount: Int,
    expirationDate: LocalDate,
    logoUrl: String,
    cardBgColor: Color,
    textColor: Color,
    cardBgImageUrl: String? = null
) {
    val cardShape = RoundedCornerShape(14.88.dp)
    Box(
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = cardShape,
                ambientColor = Color(CatchColors.GREY_7)
            )
            .background(color = cardBgColor, shape = cardShape)
            .width(224.dp)
            .height(138.44.dp)
    ) {
        if (cardBgImageUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cardBgImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = logoUrl,
                contentDescription = stringResource(id = R.string.content_description_merchant_logo),
                modifier = Modifier.height(28.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            CatchText(
                text = rewardsAmount.centsToDollarsString(),
                style = CatchTextStyles.h2,
                color = textColor,
            )
            CatchText(
                text = stringResource(R.string.card_expiration, expirationDate.format("MM/dd/yy")),
                style = CatchTextStyles.bodySmall,
                color = textColor,
            )
        }
    }
}

@Preview
@Composable
internal fun PreviewRewardCard() {
    val logoUrl =
        "https://cdn.shopify.com/s/files/1/0508/1084/7381/files/Store_Logo_Dark_180x.png?v=1614703801"
    val cardBgColor = Color.White
    val textColor = Color.Black
    CatchTheme {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {
            RewardCard(
                rewardsAmount = 1000,
                expirationDate = "2023-04-21".toLocalDate(),
                logoUrl = logoUrl,
                cardBgColor = cardBgColor,
                textColor = textColor
            )
        }
    }
}
