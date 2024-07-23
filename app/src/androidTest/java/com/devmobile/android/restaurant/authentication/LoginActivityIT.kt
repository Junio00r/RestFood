package com.devmobile.android.restaurant.authentication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.view.activities.authentication.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityIT {

    @get:Rule
    val activityScenario = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun clickButtonForRegister() {

        onView(withId(R.id.buttonRegister))
            .perform(click())
    }
}