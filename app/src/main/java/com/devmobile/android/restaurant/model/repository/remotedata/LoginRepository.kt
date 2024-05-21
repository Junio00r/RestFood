package com.devmobile.android.restaurant.model.repository.remotedata

import android.content.Context
import com.devmobile.android.restaurant.model.repository.AuthenticationResult
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(context: Context) {
    private val userDAO = RestaurantLocalDatabase.getInstance(context).getUserDao()

    suspend fun login(email: String, password: String): AuthenticationResult {

        return withContext(Dispatchers.IO) {

            if (isValidCredentials(email, password)) {

                return@withContext AuthenticationResult.Success(userDAO.findUserByEmail(email)!!)

                // Logging...

            } else {
                return@withContext AuthenticationResult.Error(IllegalArgumentException("Error: Email or password invalid"))
            }
        }
    }

    /**
     * That method verify if **email exists**, case does it, **check
     * to if password** is of correspondent email.
     *
     * @param email The email to check.
     * @param password The password to check against the email's password.
     * @return **true** if credentials already exists on database, otherwise **false**
     */
    private fun isValidCredentials(email: String, password: String): Boolean {

        // Senhas devem ser comparada usando um hash seguro
        // Possible SQL Injection and XSS Vulnerabilities
        return userDAO.findUserByEmail(email)?.password == password
    }
}