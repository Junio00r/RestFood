package com.devmobile.android.restaurant.model.datasource.remote

import com.devmobile.android.restaurant.BuildConfig

/**
 * Establish a contract with email-api
 */
interface IEmailAPIService {

    val API_KEY: String
        get() = BuildConfig.GMAIL_APY_KEY
    val BASE_URL: String
        get() = ""

    fun sendEmail(email: String, body: String)
}