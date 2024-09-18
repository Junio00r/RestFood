package com.devmobile.android.restaurant.model.datasource.remote

import android.annotation.SuppressLint
import retrofit2.Retrofit
import retrofit2.create
import kotlin.random.Random


class EmailService : IEmailAPIService {

    val retrofit = Retrofit.Builder()
        .baseUrl(GMAIL_BASE_URL)
        .build()

    val emailService = retrofit.create(IEmailAPIService::class.java)

    @SuppressLint("DefaultLocale")
    override suspend fun sendEmail(email: String, /* file with code */ body: String) {


    }

    object CodeGenerator {

        private var generator: Random = Random.Default
        private var currentCodeGenerated: String? = null

        @SuppressLint("DefaultLocale")
        fun generateCode(): String {

            currentCodeGenerated = String.format("%06d", generator.nextInt(0, 1_000_000))

            return currentCodeGenerated as String
        }

        fun currentCodeGenerated(): String? {

            return currentCodeGenerated
        }
    }
}