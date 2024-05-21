package com.devmobile.android.restaurant.model.repository.remotedata

import android.content.Context
import android.util.Log
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterRepository(private val context: Context) {

    companion object {
        val authenticationTag = "Authentication"
    }

    suspend fun register(user: User): Boolean {
        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()

        withContext(Dispatchers.IO) {

            try {

                userDao.insertUser(user)

                return@withContext true

            } catch (e: Exception) {

                Log.e(authenticationTag, "Não foi possível inserir o usuario no banco de dados")
            }
        }

        return false
    }
}