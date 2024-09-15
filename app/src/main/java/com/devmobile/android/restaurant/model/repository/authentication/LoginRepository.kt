package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.model.repository.local.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(context: Context) {
    private val userDAO = RestaurantLocalDatabase.getInstance(context).getUserDao()

    suspend fun login(email: String, password: String): RequestResult {

        return withContext(Dispatchers.IO) {

            if (isValidCredentials(email, password)) {

                return@withContext RequestResult.Success("userDAO.findUserByEmail(email)!!")

                // Logging...

            } else {
                return@withContext RequestResult.Error(IllegalArgumentException("Error: Email or password invalid"))
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