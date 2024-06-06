package com.devmobile.android.restaurant.model

class AccountException(
    message: String? = "Access account exception",
    cause: Throwable? = null
) : Exception(message, cause)