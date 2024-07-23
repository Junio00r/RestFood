package com.devmobile.android.restaurant.authentication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView
import androidx.test.espresso.assertion.ViewAssertions.matches
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
    fun testFillOutForm() {
        onView(
            allOf(
                withId(R.id.inputUserName)
            )
        ).perform(typeText("Naruto"))

//        onView(withId(R.id.inputUserLastName))
//            .perform(typeText("Uzumaki"), closeSoftKeyboard())
//
//        onView(withId(R.id.inputUserEmail))
//            .perform(typeText("narutogenin@gmail.com"), closeSoftKeyboard())
//
//        onView(withId(R.id.inputUserPassword))
//            .perform(typeText("ProximoHokage123#"), closeSoftKeyboard())


        // check
//        onView(withId(R.id.inputUserName))
//            .check(matches(withText("Naruto")))
//
//        onView(withId(R.id.inputUserLastName))
//            .check(matches(withText("Uzumaki")))
//
//        onView(withId(R.id.inputUserEmail))
//            .check(matches(withText("narutogenin@gmail.com")))
//
//        onView(withId(R.id.inputUserPassword))
//            .check(matches(withText("ProximoHokage123#")))


        // finish account's register
        onView(withId(R.id.buttonConfirmRegister)).perform(click())
    }
}