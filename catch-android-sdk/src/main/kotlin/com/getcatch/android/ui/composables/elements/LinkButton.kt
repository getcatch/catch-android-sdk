package com.getcatch.android.ui.composables.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.ui.styles.ActionButtonStyle
import com.getcatch.android.utils.launchUrlIntent

@Composable
internal fun LinkButton(
    label: String,
    link: String,
    modifier: Modifier = Modifier,
    styles: ActionButtonStyle.Resolved
) {
    val context = LocalContext.current
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = styles.backgroundColor,
            contentColor = styles.fontColor
        ),
        onClick = { launchUrlIntent(context, link) },
        modifier = modifier.height(styles.height),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = styles.applyTextTransform(label),
            style = styles.composeTextStyle,
        )
        Spacer(Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_new_tab),
            contentDescription = null,
            modifier = Modifier.size(styles.fontSize.value.dp),
            colorFilter = ColorFilter.tint(styles.fontColor)
        )
    }
}
