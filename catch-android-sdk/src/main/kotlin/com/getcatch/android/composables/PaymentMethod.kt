package com.getcatch.android.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.composables.elements.EarnRedeemText
import com.getcatch.android.composables.elements.InfoIcon
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.LocalThemeVariant
import com.getcatch.android.ui.BorderStyle

@Composable
public fun PaymentMethod(borderStyle: BorderStyle? = null) {
    CatchTheme {
        var rowModifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .animateContentSize()
        if (borderStyle != null) {
            rowModifier =
                rowModifier
                    .border(1.dp, CatchTheme.colors.border, borderStyle.shape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
        }
        Row(
            modifier = rowModifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = LocalThemeVariant.current.logoResId),
                contentDescription = stringResource(
                    id = R.string.content_description_catch_logo
                ),
                modifier = Modifier.height(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            EarnRedeemText(prefix = stringResource(R.string.pay_by_bank))
            Spacer(modifier = Modifier.width(2.dp))
            InfoIcon()
        }
    }
}
