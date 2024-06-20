package com.devmobile.android.restaurant

class AccountException(
    message: String? = "Access account exception",
    cause: Throwable? = null
) : Exception(message, cause)