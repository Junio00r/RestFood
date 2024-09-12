package com.devmobile.android.restaurant.model.repository.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.devmobile.android.restaurant.model.repository.UserRegister
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TokenVerificationRepository(
    private val context: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher),
) {
    private val codeGenerator: Random = Random.Default
    private var currentCodeGenerated: String = ""

    init {

        coroutineScope.launch {

            requestNewVerificationCode()
        }
    }

    @SuppressLint("DefaultLocale")
    suspend fun requestNewVerificationCode() {

        val codeGenerated = codeGenerator.nextInt(0, 1_000_000)
        currentCodeGenerated = String.format("%06d", codeGenerated)

        withContext(Dispatchers.IO) {

            // Uses email api to send verification code
            delay(6000).let {

                // Simulation time for request code
            }
        }

        Log.i("REQUEST", "Request New Verification Code")
    }

    suspend fun createAccount() {

        coroutineScope.launch {

            UserRegister.registerUser(context)
            Log.i("Creation", "User Account Created")
        }.join()
    }

    fun checkCode(codeEntered: String): Boolean {

        return codeEntered == currentCodeGenerated
    }
}