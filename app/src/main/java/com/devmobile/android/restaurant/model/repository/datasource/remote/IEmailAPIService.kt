package com.devmobile.android.restaurant.model.datasource.remote

import com.devmobile.android.restaurant.BuildConfig
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * Establish a contract with gmail-api
 */
interface IEmailAPIService {

    val GMAIL_API_KEY: String
        get() = BuildConfig.GMAIL_APY_KEY
    val GMAIL_BASE_URL: String
        get() = "https://gmail.googleapis.com"

    @Multipart
    @POST("/gmail/v1/users/{email}/messages/send")
    suspend fun sendEmail(@Path("email") email: String, @Part("description") last: String) : String
}