package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.UserRegister
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FormRepository(
    private val applicationContext: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun handleWithData(user: User) {

        try {

            when (hasEmailAlreadyRegistered(user.email)) {

                true -> {

                    UserRegister.sendUserData(user.name, user.lastname!!, user.email, user.password)
                }

                false -> {

                    Log.e("RegisterRepository", "Email is invalid or already taken")
                    throw Exception("Email is invalid or already taken")
                }
            }
        } catch (e: IOException) {

            Log.e("RegisterRepository", "IO database exception to verify email: ${e.message}")
            throw e

        } catch (e: SQLiteDatabaseCorruptException) {

            Log.e("RegisterRepository", "Database corrupt: ${e.message}")
            throw e

        } catch (e: SQLiteException) {

            Log.e("RegisterRepository", "SQL exception while creating account: ${e.message}")
            throw e
        }
    }

    private suspend fun hasEmailAlreadyRegistered(email: String): Boolean {
        val userDao = RestaurantLocalDatabase.getInstance(applicationContext).getUserDao()
        var canEmailRegister: Boolean

        withContext(ioDispatcher) {

            canEmailRegister = userDao.amountThisEmailRegistered(email) == 0
        }

        return canEmailRegister
    }
}