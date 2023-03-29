package com.getcatch.android.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.theming.color.CatchColors
import com.getcatch.android.utils.centsToDollarsString

@Composable
internal fun RewardCard(
    rewardsAmount: Int,
    logoUrl: String,
    cardBgColor: Color,
    textColor: Color,
    cardBgImageUrl: String? = null
) {
    val cardShape = RoundedCornerShape(14.88.dp)
    Box(
        modifier = Modifier
            .background(color = cardBgColor, shape = cardShape)
            .shadow(
                elevation = 2.dp,
                shape = cardShape,
                ambientColor = Color(CatchColors.GREY_7)
            )
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
                contentDescription = stringResource(
                    id = R.string.content_description_merchant_logo,
                ),
                modifier = Modifier.height(28.dp),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = rewardsAmount.centsToDollarsString(),
                style = CatchTypography.CatchTextStyles.h2,
                color = textColor,
            )
            Text(
                text = "Exp. 00/00/00",
                style = CatchTypography.CatchTextStyles.bodySmall,
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
        RewardCard(
            rewardsAmount = 1000,
            logoUrl = logoUrl,
            cardBgColor = cardBgColor,
            textColor = textColor
        )
    }
}
