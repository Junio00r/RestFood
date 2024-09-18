package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import android.util.Log
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class TokenVerificationRepository(
    private val context: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher),
) {
    private val emailCommunicationHandler = EmailApiService()

    fun requestNewVerificationCode(email: String) {

        // Uses email api to send verification code
        coroutineScope.launch {

            val result =
                emailCommunicationHandler.emailService.sendEmail(email = email, last = "Testing...")
        }
    }

    suspend fun createAccount() {

        coroutineScope.launch {

            Log.i("Creation", "User Account Created")
        }.join()
    }
}