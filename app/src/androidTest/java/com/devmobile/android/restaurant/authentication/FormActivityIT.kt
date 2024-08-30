package com.devmobile.android.restaurant.authentication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.view.activities.authentication.FormActivity
import com.google.android.material.textfield.TextInputLayout
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
                withChild(withId(R.id.inputUserName))
            )
        ).perform(typeText("Naruto"))

        onView(
            allOf(
                withId(R.id.inputUserLastName)
            )
        ).perform(typeText("Uzumaki"))

        onView(
            allOf(
                withId(R.id.inputUserEmail)
            )
        ).perform(typeText("narutogenin@gmail.com"))

        onView(
            allOf(
                withId(R.id.inputUserPassword)
            )
        ).perform(typeText("NarutoHokage123#"), closeSoftKeyboard())

        // In case of config changing
        activityScenario.scenario.onActivity { activity ->

            activity.recreate()

        }

        onView(
            allOf(
                withId(R.id.inputUserName)
            )
        ).check(matches(withText("Naruto")))
4
        onView(
            allOf(
                withId(R.id.inputUserLastName)
            )
        ).check(matches(withText("Uzumaki")))

        onView(
            allOf(
                withId(R.id.inputUserEmail)
            )
        ).check(matches(withText("narutogenin@gmail.com")))

        onView(
            allOf(
                withId(R.id.inputUserPassword)
            )
        ).check(matches(withText("NarutoHokage123#")))

//        Thread.sleep(5000)
    }
}