package com.abramchuk.itbookstore

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class AccountUITest {
    private val login = "test"
    private val password = "test"

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickLoginButton_opensSearchFragment() {
        onView(withId(R.id.login_editText))
                .perform(ViewActions.typeText(login))
        onView(withId(R.id.password_editText))
                .perform(ViewActions.typeText(password))
        onView(withId(R.id.loginBtn))
                .perform(ViewActions.click())
        onView(withId(R.id.search_btn))
                .check(matches(withText("FIND")))
    }

    @Test
    fun clickLogoutButton_opensLoginFragment() {
        onView(withId(R.id.navigation_notifications))
                .perform(ViewActions.click())
        onView(withId(R.id.logout_btn))
                .perform(ViewActions.click())
        onView(withId(R.id.regNavBtn))
                .check(matches(withText("REGISTRATION")))
    }
}