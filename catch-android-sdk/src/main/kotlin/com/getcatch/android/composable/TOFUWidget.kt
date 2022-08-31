package com.getcatch.android.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.util.centsToDollarsString
import com.getcatch.android.util.constrainToParentHeight

@Composable
public fun TOFUWidget(redeemableRewards: Int?) {
    val displayText: String = when (redeemableRewards) {
        null -> stringResource(id = R.string.tofu_earn_placeholder)
        else -> stringResource(
            id = R.string.tofu_redeem_amount,
            redeemableRewards.centsToDollarsString()
        )
    }
    Row(
        modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(displayText)
        Image(
            painter = painterResource(id = R.drawable.ic_catch_logo),
            contentDescription = stringResource(
                id = R.string.content_description_catch_logo
            ),
            contentScale = ContentScale.Fit,
            modifier = Modifier.constrainToParentHeight()

        )
    }
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
        TOFUWidget(redeemableRewards = 1994)
    }
}
