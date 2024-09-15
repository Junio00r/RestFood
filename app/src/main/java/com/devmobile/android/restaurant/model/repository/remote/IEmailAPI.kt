package com.devmobile.android.restaurant.model.repository.remote

/**
 * Establish a contract with email-api
 */
interface EmailAPI {

    val EMAIL_APY_KEY_SECRET: String
        get() = ""

    suspend fun sendEmail(email: String)
}