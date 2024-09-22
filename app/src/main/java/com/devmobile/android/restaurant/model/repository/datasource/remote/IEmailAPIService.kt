package com.devmobile.android.restaurant.model.repository.datasource.remote

import com.devmobile.android.restaurant.BuildConfig
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Establish a contract with gmail-api
 */
interface IEmailAPIService {

    @Multipart // Used for send a file .html
    @POST("")
    suspend fun sendEmail(
        @Path("email") email: String,
        @Body messageFile: RequestBody
    ): Response<EmailResponse>
}