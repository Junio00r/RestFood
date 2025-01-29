package com.devmobile.android.restaurant.model.repository

import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val localDatabase: RestaurantLocalDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun makeRequestLogin(email: String, password: String) {

        if (!checkExistenceOnDatabase(email, password)) {

            throw IllegalArgumentException("Email or password invalid")
        }
    }

    /**
     * That method verify if **email exists**, case does it, **check
     * if password** is of correspondent email.
     *
     * @param email The email to check.
     * @param password The password to check against the email's password.
     * @return **true** if credentials already exists on database, otherwise **false**
     */
    private suspend fun checkExistenceOnDatabase(email: String, password: String): Boolean {

        // Possible SQL Injection and XSS Vulnerabilities
        return withContext(ioDispatcher) {

            localDatabase.getUserDao().findUserByEmail(email)?.password == password
        }
    }
}