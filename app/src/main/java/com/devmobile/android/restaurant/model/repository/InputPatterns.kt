package com.devmobile.android.restaurant.model.repository

import java.util.regex.Pattern

class InputPatterns {

    companion object {

        val USERNAME_PATTERN: Pattern = Pattern.compile(
            "^(?=.*[a-zA-Z])\\w{3,25}\$"
        )

        val EMAIL_PATTERN: Pattern = Pattern.compile(
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])\\S{6,}\$"
        )

        val PASSWORD_PATTERN: Pattern = Pattern.compile(

            "^" + "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$"
        )

        fun matcher(pattern: Pattern, data: String?): Boolean {

            if (data != null) {
                return when (pattern) {

                    USERNAME_PATTERN -> {
                        return USERNAME_PATTERN.matcher(data).matches()
                    }

                    EMAIL_PATTERN -> {
                        return EMAIL_PATTERN.matcher(data).matches()
                    }

                    PASSWORD_PATTERN -> {
                        return PASSWORD_PATTERN.matcher(data).matches()
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