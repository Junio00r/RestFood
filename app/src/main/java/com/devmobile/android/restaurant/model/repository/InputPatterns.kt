package com.devmobile.android.restaurant.model.repository

import java.util.regex.Pattern

class InputPatterns {

    companion object {

        // Error Messages
        const val TEXT_ERROR_MESSAGE =

            "Texto não pode conter caracteres (!, @ # $ ¨ %) especias, numeros e nem espaços"
        const val TEXT_NAME_ERROR_MESSAGE = "Name invalid"

        const val PASSWORD_ERROR_MESSAGE =
            "Password have must in minimum 8 characters, three numbers and at least one special character (\$,*, -)."

        const val EMAIL_ERROR_MESSAGE = "Email is invalid or already taken"

        // Patterns
        val TEXT_PATTERN: Pattern = Pattern.compile(
            "^(?=.*[a-zA-Z])\\w{3,25}\$"
        )

        val EMAIL_PATTERN: Pattern = Pattern.compile(
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"
        )

        val PASSWORD_PATTERN: Pattern = Pattern.compile(

            "(?=^.{8,}\$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*\$"
        )


        // Function
        fun isMatch(patternToCheck: Pattern, data: String?): Boolean {

            if (data != null) {
                return when (patternToCheck) {

                    TEXT_PATTERN -> {
                        return TEXT_PATTERN.matcher(data.trim()).matches()
                    }

                    EMAIL_PATTERN -> {
                        return EMAIL_PATTERN.matcher(data.trim()).matches()
                    }

                    PASSWORD_PATTERN -> {
                        return PASSWORD_PATTERN.matcher(data.trim()).matches()
                    }

                    else -> {
                        false
                    }
                }
            }

            return false
        }
    }
}