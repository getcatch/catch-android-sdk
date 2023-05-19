package com.getcatch.sample.ui.composables.demos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.getcatch.android.models.checkout.CheckoutPrefill
import com.getcatch.sample.ui.composables.DemoSection
import com.getcatch.sample.ui.theming.demoCheckboxColors
import com.getcatch.sample.utils.CheckoutDemoOption

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OpenCheckoutDemo(
    handleOpenCheckout: (CheckoutDemoOption, String, CheckoutPrefill) -> Unit
) {
    var checkoutOption by remember { mutableStateOf(CheckoutDemoOption.DIRECT_OPEN) }
    var checkoutId by remember { mutableStateOf("") }
    var prefillPhone by remember { mutableStateOf("") }
    var prefillName by remember { mutableStateOf("") }
    var prefillEmail by remember { mutableStateOf("") }

    val checkoutIdFocusRequester = remember { FocusRequester() }
    val phoneFocusRequester = remember { FocusRequester() }
    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val formFieldModifier = Modifier.fillMaxWidth()

    DemoSection(
        title = "Open Checkout",
        widgetContent = {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    value = checkoutId,
                    onValueChange = { checkoutId = it },
                    label = { Text(checkoutOption.idLabel) },
                    singleLine = true,
                    modifier = formFieldModifier.focusRequester(checkoutIdFocusRequester),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { phoneFocusRequester.requestFocus() })
                )
                TextField(
                    value = prefillPhone,
                    onValueChange = { prefillPhone = it },
                    label = { Text("User Phone") },
                    singleLine = true,
                    modifier = formFieldModifier.focusRequester(phoneFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Phone
                    ),
                    keyboardActions = KeyboardActions(onNext = { nameFocusRequester.requestFocus() })
                )
                TextField(
                    value = prefillName,
                    onValueChange = { prefillName = it },
                    label = { Text("User Name") },
                    singleLine = true,
                    modifier = formFieldModifier.focusRequester(nameFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words
                    ),
                    keyboardActions = KeyboardActions(onNext = { emailFocusRequester.requestFocus() })
                )
                TextField(
                    value = prefillEmail,
                    onValueChange = { prefillEmail = it },
                    label = { Text("User Email") },
                    singleLine = true,
                    modifier = formFieldModifier.focusRequester(emailFocusRequester),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                )
                Button(
                    onClick = {
                        handleOpenCheckout(
                            checkoutOption,
                            checkoutId,
                            CheckoutPrefill(
                                userPhone = prefillPhone,
                                userName = prefillName,
                                userEmail = prefillEmail
                            )
                        )
                    },
                    enabled = checkoutId.isNotBlank(),
                    modifier = formFieldModifier,
                ) {
                    Text(checkoutOption.buttonLabel, modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        },
        settingsContent = {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text("Integration Type", fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checkoutOption == CheckoutDemoOption.DIRECT_OPEN,
                        onCheckedChange = { checkoutOption = CheckoutDemoOption.DIRECT_OPEN },
                        colors = demoCheckboxColors
                    )
                    Text(CheckoutDemoOption.DIRECT_OPEN.label)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checkoutOption == CheckoutDemoOption.VIRTUAL_CARD_OPEN,
                        onCheckedChange = { checkoutOption = CheckoutDemoOption.VIRTUAL_CARD_OPEN },
                        colors = demoCheckboxColors
                    )
                    Text(CheckoutDemoOption.VIRTUAL_CARD_OPEN.label)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checkoutOption == CheckoutDemoOption.VIRTUAL_CARD_CREATE,
                        onCheckedChange = {
                            checkoutOption = CheckoutDemoOption.VIRTUAL_CARD_CREATE
                        },
                        colors = demoCheckboxColors
                    )
                    Text(CheckoutDemoOption.VIRTUAL_CARD_CREATE.label)
                }
            }
        }
    )
}
