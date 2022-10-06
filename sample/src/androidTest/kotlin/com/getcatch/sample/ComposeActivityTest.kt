package com.getcatch.sample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComposeActivityTest {

    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<ComposeActivity>()

    @Test
    fun onClickTofuWidget_redeemText() {
        // By default should say Earn
        composeTestRule.onNodeWithText("Pay by bank.", substring = true).assertIsDisplayed()
    }
}
