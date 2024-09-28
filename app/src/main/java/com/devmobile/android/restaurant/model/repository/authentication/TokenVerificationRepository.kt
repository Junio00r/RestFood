package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailApiService
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenVerificationRepository(
    private val context: Context,
    private val emailCommunicationHandler: EmailApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun requestNewVerificationCode(objectBody: EmailRequest) {

        // Uses email api to send verification code
        withContext(ioDispatcher) {

            val response = emailCommunicationHandler.emailService.sendEmail(objectBody)
        }
    }

    suspend fun createAccount() {

    }
}
