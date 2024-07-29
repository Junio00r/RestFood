package com.devmobile.android.restaurant.authentication

import com.devmobile.android.restaurant.viewmodel.InputPatterns
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FormActivityLT {

    val emailsInvalid = arrayOf(
        "tetopetgmail.com",
        "toeiu'@gmail.com",
        "@gmail.com",
        "TPueopteitop@.com.br",
        "eopteitop@.com.br",
        "teteio@",
        ",",
        "."
    )
    val emailsValid = arrayOf(
        "tetopet@gmail.com",
        "toeiu@gmail.com",
        "tioeutioe@gmail.com",
        "tioeutioe+@gmail.com",
        "toeuo293@gmail.com",
        "oietuoeituiotueioutoieutioeutioueiotuioetuoieutoieutioeuiotuet@gmail.com",
        "uetoiutoietu@gmail.com.br.br.br.br",
    )

    @Test
    fun textPattern_CheckSupportName() {

        assertTrue(
            InputPatterns.isMatch(
                InputPatterns.TEXT_PATTERN, "Kakashi Hatake"
            ).first
        )
    }

    @Test
    fun emailPattern_CheckSupportEmail() {

        emailsInvalid.forEach { email ->

            assertFalse(
                "Email valid: $email", InputPatterns.isMatch(
                    InputPatterns.EMAIL_PATTERN, email
                ).first
            )
        }

        emailsValid.forEach { email ->

            assertTrue(
                "Email invalid: $email", InputPatterns.isMatch(
                    InputPatterns.EMAIL_PATTERN, email
                ).first
            )
        }
    }

    @Test
    fun passwordPattern_CheckSupportPassword() {

        assertTrue(
            InputPatterns.isMatch(
                InputPatterns.PASSWORD_PATTERN, "Charigan1234#"
            ).first
        )
    }
}