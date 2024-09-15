package com.devmobile.android.restaurant.model

import android.annotation.SuppressLint
import com.devmobile.android.restaurant.model.repository.remote.IEmailAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class EmailHandler : IEmailAPI {

    private val codeGenerator: Random = Random.Default
    private var currentCodeGenerated: String? = null

    @SuppressLint("DefaultLocale")
    suspend fun handlerVerificationCode(email: String) {
        val codeGenerated = codeGenerator.nextInt(0, 1_000_000)
        currentCodeGenerated = String.format("%06d", codeGenerated)

        withContext(Dispatchers.IO) {

            sendEmail(currentCodeGenerated!!)
        }
    }

    fun getCodeSent(): String? {
        return currentCodeGenerated
    }

    override suspend fun sendEmail(email: String) {

    }
}