package com.devmobile.android.restaurant.model.repository.datasource.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class EmailInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originRequest = chain.request()
        val newRequest = originRequest.newBuilder()
            .addHeader("accept", "application/json")
            .addHeader("Connection", "close")
            .addHeader("api-key", apiKey)
            .addHeader("content-type", "application/json")
            .build()

        Log.i("Email Service", "Request a verification email for Brevo API")

        val response = chain.proceed(newRequest)
        Log.i("Email Service", "Response a verification email for Brevo API")

        return response
    }
}