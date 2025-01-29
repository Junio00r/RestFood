package com.devmobile.android.restaurant.model.repository

import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.devmobile.android.restaurant.model.datasource.local.RestaurantLocalDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FormRepository(
    private val localDatabase: RestaurantLocalDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun hasEmailAlreadyRegistered(email: String): Boolean {
        var canEmailRegister: Boolean

        withContext(ioDispatcher) {

            try {

                canEmailRegister = localDatabase.getUserDao().amountRegisteredEmail(email) == 0

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