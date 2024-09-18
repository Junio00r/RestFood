package com.devmobile.android.restaurant.model.repository.datasource.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EmailApiService {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://gmail.googleapis.com")
        .build()

    val emailService: IEmailAPIService by lazy {
        retrofit.create(IEmailAPIService::class.java)
    }

    val testing = "TYeetnte"
}
