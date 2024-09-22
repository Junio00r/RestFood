package com.devmobile.android.restaurant.model.repository.datasource.remote

import com.devmobile.android.restaurant.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmailApiService {

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(EmailInterceptor(apiKey = BuildConfig.GMAIL_APY_KEY))
        .build()

    private val retrofit = Retrofit.Builder()
        // set a converter for parser a java/kotlin object in a gson and vice versa. Requests and responses
        .addConverterFactory(GsonConverterFactory.create())
        // URL for request
        .baseUrl("https://api.sendpulse.com")
        .client(okHttp)
        .build()

    val emailService: IEmailAPIService by lazy {

        retrofit.create(IEmailAPIService::class.java)
    }
}

data class EmailRequest()

data class EmailResponse()
