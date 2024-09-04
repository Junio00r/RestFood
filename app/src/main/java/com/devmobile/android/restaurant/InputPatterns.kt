package com.devmobile.android.restaurant

import java.util.regex.Pattern

object InputPatterns {

    // Error Messages
    private const val TEXT_ERROR_MESSAGE =
        "Texto não pode conter letras (!, @ # $ ¨ %) especias, numeros e nem espaços"
    private const val TEXT_NAME_ERROR_MESSAGE = "Invalid Name. The name must contain only letters"
    private const val PASSWORD_ERROR_MESSAGE =
        "Password have must in minimum 8 characters, three numbers and at least one special character (\$,*, -)."
    private const val EMAIL_ERROR_MESSAGE = "Email is invalid or already taken"
    private const val NUMBER_ERROR_MESSAGE = "Deve conter apenas números"

    // Patterns
    @JvmStatic
    val TEXT_PATTERN: Pattern = Pattern.compile(
        "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžæÀÁÂÄÃÅĄĆČĐđĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+\$"
    )

    // 0.1 pottern = (?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
    //                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
    //                "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
    //                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
    //                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])
    @JvmStatic
    val EMAIL_PATTERN: Pattern = Pattern.compile(
        "(?:[a-z0-9!#$%&*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.)" +
                "{3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"
    )

    @JvmStatic
    val PASSWORD_PATTERN: Pattern = Pattern.compile(

        "(?=^.{8,}\$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[a-z]).*\$"
    )

    @JvmStatic
    val NUMBER_PATTERN: Pattern = Pattern.compile(

        "^\\d+\$"
    )


    // Function
    @JvmStatic
    fun isMatch(patternToCheck: Pattern, data: String?): Pair<Boolean, String?> {

        if (data != null) {
            return when (patternToCheck) {

                TEXT_PATTERN -> {
                    return Pair(
                        TEXT_PATTERN.matcher(data.trim()).matches(),
                        TEXT_NAME_ERROR_MESSAGE
                    )
                }

                EMAIL_PATTERN -> {
                    return Pair(
                        EMAIL_PATTERN.matcher(data.trim()).matches(),
                        EMAIL_ERROR_MESSAGE
                    )
                }

                PASSWORD_PATTERN -> {
                    return Pair(
                        PASSWORD_PATTERN.matcher(data.trim()).matches(),
                        PASSWORD_ERROR_MESSAGE
                    )
                }

                NUMBER_PATTERN -> {
                    return Pair(
                        NUMBER_PATTERN.matcher(data.trim()).matches(),
                        NUMBER_ERROR_MESSAGE
                    )
                }

                else -> {
                    Pair(false, TEXT_ERROR_MESSAGE)
                }
            }
        }

        return Pair(false, TEXT_ERROR_MESSAGE)
    }
}