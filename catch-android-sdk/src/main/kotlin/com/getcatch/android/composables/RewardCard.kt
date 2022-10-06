package com.getcatch.android.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.getcatch.android.R
import com.getcatch.android.theming.CatchRawColors
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.utils.centsToDollarsString

@Composable
internal fun RewardCard(rewardsAmount: Int, logoUrl: String, cardBgColor: Color, textColor: Color) {
    val cardShape = RoundedCornerShape(14.88.dp)
    Column(
        modifier = Modifier
            .background(color = cardBgColor, shape = cardShape)
            .shadow(
                elevation = 2.dp,
                shape = cardShape,
                ambientColor = Color(CatchRawColors.GREY_7)
            )
            .width(224.dp)
            .height(138.44.dp)
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
