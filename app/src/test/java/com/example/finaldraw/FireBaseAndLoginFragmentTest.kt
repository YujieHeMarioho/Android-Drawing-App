package com.example.finaldraw

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FireBaseAndLoginFragmentTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testFirebaseFragmentDisplays() {
        onView(withId(R.id.textViewData))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLoginWithEmptyCredentialsShowsError() {
        onView(withId(R.id.editTextEmail))
            .perform(clearText())

        onView(withId(R.id.editTextPassword))
            .perform(clearText())

        onView(withId(R.id.loginButton))
            .perform(click())

        onView(withText("Username"))
            .check(matches(isDisplayed()))

        onView(withText("Password"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testVisibilityOfUIElements() {
        onView(withId(R.id.editTextEmail))
            .check(matches(isDisplayed()))

        onView(withId(R.id.editTextPassword))
            .check(matches(isDisplayed()))

        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLoginButtonIsEnabledInitially() {
        onView(withId(R.id.loginButton))
            .check(matches(isEnabled()))
    }

}
