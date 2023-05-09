package com.getcatch.sample.ui.composables.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.getcatch.android.ui.PaymentMethodVariant
import com.getcatch.android.ui.composables.PaymentMethod
import com.getcatch.sample.ui.composables.DemoSection
import com.getcatch.sample.ui.composables.SegmentedControl
import com.getcatch.sample.ui.theming.demoCheckboxColors
import com.getcatch.sample.utils.allPaymentMethodVariants
import com.getcatch.sample.utils.name

@Composable
fun PaymentMethodDemo(price: Int) {
    var paymentMethodVariant: PaymentMethodVariant by remember { mutableStateOf(PaymentMethodVariant.Standard) }
    var disabled by remember { mutableStateOf(false) }
    DemoSection(title = "Payment Method", widgetContent = {
        PaymentMethod(price = price, variant = paymentMethodVariant, disabled = disabled)
    }, settingsContent = {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Variant")
            SegmentedControl(
                options = allPaymentMethodVariants.map { it.name },
                onOptionSelected = { paymentMethodVariant = allPaymentMethodVariants[it] }
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = disabled,
                onCheckedChange = { disabled = it },
                colors = demoCheckboxColors
            )
            Text("Disabled")
        }
    })
}
