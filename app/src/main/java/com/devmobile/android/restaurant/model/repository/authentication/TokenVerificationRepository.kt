package com.devmobile.android.restaurant.model.repository.authentication

import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import com.devmobile.android.restaurant.model.datasource.local.entities.User
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailApiService
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TokenVerificationRepository(
    private val localDatabase: RestaurantLocalDatabase,
    private val emailCommunicationHandler: EmailApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun requestNewVerificationCode(objectBody: EmailRequest) {

        // Uses email api to send verification code
        withContext(ioDispatcher) {

            val response = emailCommunicationHandler.emailService.sendEmail(objectBody)
        }
    }

    suspend fun createAccount(user: User) {

        withContext(ioDispatcher) {

            localDatabase.getUserDao().insertUser(user)
        }
    }
}
