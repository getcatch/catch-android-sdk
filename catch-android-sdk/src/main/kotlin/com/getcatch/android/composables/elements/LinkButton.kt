package com.getcatch.android.composables.elements

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.getcatch.android.R
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.theming.atomization.atoms.ActionButtonAtom

@Composable
internal fun LinkButton(
    label: String,
    link: String,
    actionButtonAtom: ActionButtonAtom.Resolved? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val atoms = actionButtonAtom ?: CatchTheme.atoms.actionButton
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = atoms.backgroundColor,
            contentColor = atoms.fontColor,
        ),
        onClick = {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            context.startActivity(browserIntent)
        },
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(12.dp)
    ) {
        Text(
            text = label,
            style = CatchTypography.CatchTextStyles.buttonLabel,
        )
        Spacer(modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_new_tab),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
    }
}
