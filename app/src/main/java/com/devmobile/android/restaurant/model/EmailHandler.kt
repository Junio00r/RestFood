package com.devmobile.android.restaurant.model

import android.annotation.SuppressLint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class EmailHandler {

    companion object {
        const val EMAIL_API_KEY = "Nothing"
    }

    private val codeGenerator: Random = Random.Default
    private var currentCodeGenerated: String? = null

    @SuppressLint("DefaultLocale")
    suspend fun handlerVerificationCode(email: String) {
        val codeGenerated = codeGenerator.nextInt(0, 1_000_000)
        currentCodeGenerated = String.format("%06d", codeGenerated)

        withContext(Dispatchers.IO) {

            sendCode(currentCodeGenerated!!)
        }
    }

    fun getCodeSent() : String? {
        return currentCodeGenerated
    }

    private suspend fun sendCode(code: String) {

    }
}