package com.devmobile.android.restaurant.model.datasource.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Establish a contract with gmail-api
 */
interface IEmailAPIService {

    @GET(".")
    suspend fun checkService(): Response<ResponseBody>

    @POST("v3/smtp/email/")
    suspend fun sendEmail(@Body emailRequest: EmailRequest): Response<EmailResponse>
}