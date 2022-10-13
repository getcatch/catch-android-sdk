package com.getcatch.android.composables.elements

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.activities.tofu.TOFUActivity
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.CatchTypography

@Composable
internal fun InfoIcon() {
    val context = LocalContext.current
    Text(
        text = stringResource(R.string.info_character),
        style = CatchTypography.CatchTextStyles.bodySmall,
        modifier = Modifier
            .padding(2.dp)
            .clickable(
                onClick = {
                    val intent = Intent(context, TOFUActivity::class.java)
                    context.startActivity(intent)
                },
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
    )
}

@Preview
@Composable
private fun PreviewInfoIcon() {
    CatchTheme {
        InfoIcon()
    }
}
