package com.devmobile.android.restaurant.model.repository.datasource.remote

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class EmailInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val originRequest = chain.request()

        val newRequest = originRequest.newBuilder()
            .headers(
                Headers.of(
                    "Authorization: ", "Bearer $apiKey",
                )
            )
            .build()
        Log.i("Email Service", "Request a verification email for Malignum API")

        val response = chain.proceed(newRequest)
        Log.i("Email Service", "Response Obtained")

        return response
    }
}