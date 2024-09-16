package com.devmobile.android.restaurant.model.datasource.remote

import android.annotation.SuppressLint
import kotlin.random.Random

class EmailCommunicationHandler : IEmailAPIService {

    fun getCodeSent(): String? {
        return CodeGenerator.currentCodeGenerated()
    }

    @SuppressLint("DefaultLocale")
    override fun sendEmail(email: String, /* file with code */ body: String) {


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