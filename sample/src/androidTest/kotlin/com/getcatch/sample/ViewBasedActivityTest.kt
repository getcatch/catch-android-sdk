package com.getcatch.sample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
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
        onView(withText("Earn 10% when you pay with")).check(matches(isDisplayed()))
        composeTestRule.onNodeWithText("Earn 10% when you pay with").assertIsDisplayed()
    }

    @Test
    fun onClickTofuView_redeemText() {
        // Check earn text
        val classicalView =
            onView(allOf(withId(R.id.test_tofu_view), withChild(withSubstring("Earn"))))
        classicalView.check(
            matches(
                isDisplayed()
            )
        )
        composeTestRule.onNodeWithText("Earn", substring = true).assertIsDisplayed()

        // Click views
        classicalView.perform(click())
        onView(withId(R.id.test_compose_tofu_view)).perform(click())

        // Check text updated
        onView(
            allOf(
                withId(R.id.test_tofu_view),
                withChild(withSubstring("Redeem"))
            )
        ).check(matches(isDisplayed()))
        composeTestRule.onNodeWithText("Redeem", substring = true).assertIsDisplayed()
    }

}
