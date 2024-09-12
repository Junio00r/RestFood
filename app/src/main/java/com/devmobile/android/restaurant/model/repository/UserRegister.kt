package com.devmobile.android.restaurant.model.repository

import android.content.Context
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UserRegister {
    private lateinit var user: User

    fun sendUserData(name: String, lastName: String, email: String, password: String) {

        user = User(
            id = null,
            name = name,
            lastname = lastName,
            email = email,
            password = password,
        )
    }

    suspend fun registerUser(context: Context) {
        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()

        withContext(Dispatchers.IO) {

            try {

                userDao.insertUser(user)

            } catch (e: Exception) {

                throw Exception("Not was possible register the user")
            }
        }
    }
}