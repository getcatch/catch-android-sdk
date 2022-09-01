package com.getcatch.sample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
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
        composeTestRule.onNodeWithText("Earn", substring = true).assertIsDisplayed()

        // Click to trigger change in TOFUWidget
        composeTestRule.onNodeWithTag("TOFUWidgetWrapper").performClick()

        // Check node updated with new value
        composeTestRule.onNodeWithText("Redeem", substring = true).assertIsDisplayed()
    }
}