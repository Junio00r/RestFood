package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import android.util.Log
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailApiService
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TokenVerificationRepository(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val emailCommunicationHandler: EmailApiService,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher),
) {

    suspend fun requestNewVerificationCode(emailRequest: EmailRequest) {

        // Uses email api to send verification code
        withContext(ioDispatcher) {

            val response = emailCommunicationHandler.emailService.sendEmail(emailRequest)
        }
    }

    suspend fun createAccount() {

        coroutineScope.launch {

            Log.i("Creation", "User Account Created")
        }.join()
    }
}
