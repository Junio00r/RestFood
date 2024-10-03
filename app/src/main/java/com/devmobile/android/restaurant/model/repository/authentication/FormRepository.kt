package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FormRepository(
    private val applicationContext: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val userDao = RestaurantLocalDatabase.getInstance(applicationContext).getUserDao()

    suspend fun hasEmailAlreadyRegistered(email: String): Boolean {
        var canEmailRegister: Boolean

        withContext(ioDispatcher) {

            try {

                canEmailRegister = userDao.amountRegisteredEmail(email) == 0

                if (!canEmailRegister) {

                    throw Exception("Email is invalid or already taken")
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

        return true
    }
}