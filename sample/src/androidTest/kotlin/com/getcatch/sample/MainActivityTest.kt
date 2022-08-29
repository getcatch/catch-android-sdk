package com.getcatch.sample

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun onCreated_earnText() {
        onView(withText("Earn 10% when you pay with")).check(matches(isDisplayed()))
    }

    @Test
    fun onClickTofuView_redeemText() {
        onView(withSubstring("Redeem")).check(doesNotExist())
        onView(withId(R.id.test_tofu_view)).perform(click())
        onView(withSubstring("Redeem")).check(matches(isDisplayed()))
    }

}