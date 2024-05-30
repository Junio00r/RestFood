package com.devmobile.android.restaurant.model.repository

import java.util.regex.Pattern

class InputPatterns {

    companion object {

        val TEXT_PATTERN: Pattern = Pattern.compile(
            "^(?=.*[a-zA-Z])\\w{3,25}\$"
        )

        val EMAIL_PATTERN: Pattern = Pattern.compile(
            "/[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/gi"
        )

        val PASSWORD_PATTERN: Pattern = Pattern.compile(

            "(?=^.{8,}\$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*\$"
        )

        fun matcher(pattern: Pattern, data: String?): Boolean {

            if (data != null) {
                return when (pattern) {

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