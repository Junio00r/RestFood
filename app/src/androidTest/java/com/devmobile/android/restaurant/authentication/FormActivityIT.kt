package com.devmobile.android.restaurant.authentication

import android.content.res.Configuration
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.view.activities.authentication.FormActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FormActivityIT {

    @get:Rule
    val activityScenario = ActivityScenarioRule(FormActivity::class.java)

    @Test
    fun fillOutForm() {

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserName)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("Naruto"))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserLastName)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("Uzumaki"))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserEmail)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("narutogenin@gmail.com"))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserPassword)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("NarutoHokage123#"), closeSoftKeyboard())

        // In case of config changing
        activityScenario.scenario.onActivity { activity ->

            activity.recreate()

        }

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserName)),
                withId(R.id.textInputEditText)
            )
        ).check(matches(withText("Naruto")))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserLastName)),
                withId(R.id.textInputEditText)
            )
        ).check(matches(withText("Uzumaki")))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserEmail)),
                withId(R.id.textInputEditText)
            )
        ).check(matches(withText("narutogenin@gmail.com")))

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserPassword)),
                withId(R.id.textInputEditText)
            )
        ).check(matches(withText("NarutoHokage123#")))

        Thread.sleep(5000)
    }
}