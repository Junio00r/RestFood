package com.devmobile.android.restaurant.model.repository

import java.util.regex.Pattern

class InputPatterns {

    companion object {

        val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$"
        )
    }
}