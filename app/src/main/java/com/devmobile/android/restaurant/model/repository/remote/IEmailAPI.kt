package com.devmobile.android.restaurant.model.repository.remote

import com.devmobile.android.restaurant.BuildConfig

/**
 * Establish a contract with email-api
 */
interface IEmailAPI {

    val emailApiKey: String
        get() = BuildConfig.GMAIL_APY_KEY

    suspend fun sendEmail(email: String, body: String)
}