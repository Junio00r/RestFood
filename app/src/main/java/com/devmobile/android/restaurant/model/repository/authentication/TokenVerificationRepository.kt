package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import android.util.Log
import com.devmobile.android.restaurant.model.datasource.remote.EmailCommunicationHandler
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
    private val emailCommunicationHandler = EmailCommunicationHandler()

    fun requestNewVerificationCode(email: String) {

        // Uses email api to send verification code
        coroutineScope.launch {

            emailCommunicationHandler.sendEmail(email = email, body = "")
        }
        Log.i("REQUEST", "Request New Verification Code")
    }

    suspend fun createAccount() {

        coroutineScope.launch {

            Log.i("Creation", "User Account Created")
        }.join()
    }

    fun checkCode(codeEntered: String): Boolean {

        return codeEntered == (emailCommunicationHandler.getCodeSent() ?: "")
    }
}