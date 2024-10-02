package com.devmobile.android.restaurant

import com.devmobile.android.restaurant.usecase.InputPatterns
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val emailToCheck = arrayOf(
        "oruioeurioute@gmail.com",

    )
    @Test
    fun addition_isCorrect() {

        assertTrue(
            InputPatterns.isMatch(
                InputPatterns.EMAIL_PATTERN,
                "oruioeurioute@gmail.com"
            ).isMatch
        )
    }
}