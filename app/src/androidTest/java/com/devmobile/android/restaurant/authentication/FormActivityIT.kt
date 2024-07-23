package com.devmobile.android.restaurant.authentication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
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
    fun testFillOutForm() {

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserName)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("Naruto"), closeSoftKeyboard())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserLastName)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("Uzumaki"), closeSoftKeyboard())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserEmail)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("narutogenin@gmail.com"), closeSoftKeyboard())

        onView(
            allOf(
                isDescendantOfA(withId(R.id.inputUserPassword)),
                withId(R.id.textInputEditText)
            )
        ).perform(typeText("NarutoHokage123#"), closeSoftKeyboard())

        // finish account's register
        onView(withId(R.id.buttonConfirmRegister)).perform(click())
    }
}