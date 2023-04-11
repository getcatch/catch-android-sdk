package com.getcatch.sample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewBasedActivityTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<ViewBasedActivity>()

    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<ViewBasedActivity>()

    @Test
    fun onCreated_earnText() {
        onView(withId(R.id.basic_callout)).check(matches(isDisplayed()))
        composeTestRule.onNodeWithText("earn 10% credit").assertIsDisplayed()
    }
}
