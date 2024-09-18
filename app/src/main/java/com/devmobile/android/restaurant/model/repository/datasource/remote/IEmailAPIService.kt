package com.devmobile.android.restaurant.model.repository.datasource.remote

import com.devmobile.android.restaurant.BuildConfig
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Establish a contract with gmail-api
 */
interface IEmailAPIService {

    val GMAIL_API_KEY: String
        get() = BuildConfig.GMAIL_APY_KEY
    val GMAIL_BASE_URL: String
        get() = "https://gmail.googleapis.com"

    @Multipart // Used for send a file .html
    @POST("/gmail/v1/users/{email}/messages/send")
    suspend fun sendEmail(
        @Path("email") email: String,
        @Body messageFile: RequestBody
    ): String
}