package com.devmobile.android.restaurant.model.repository.remotedata

import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.devmobile.android.restaurant.AccountException
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FormRepository(private val context: Context) {

    suspend fun hasEmailAlreadyRegistered(email: String): Boolean {

        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()
        var result: Boolean

        withContext(Dispatchers.IO) {

            try {

                result = userDao.hasEmailRegistered(email) != 0

            } catch (e: IOException) {

                Log.e(
                    "RegisterRepository", "IO database exception to verify email: ${e.message}"
                )
                throw e

            } catch (e: SQLiteDatabaseCorruptException) {

                Log.e(
                    "Test RegisterRepository", "Database corrupt: ${e.message}"
                )
                throw e

            } catch (e: SQLiteException) {

                Log.e(
                    "RegisterRepository", "SQL exception while creating account: ${e.message}"
                )
                throw e

            } catch (e: AccountException) {

                Log.e(
                    "RegisterRepository",
                    "Unexpected exception while check whether valid email : ${e.message}"
                )
                throw e
            }
        }
        return result
    }
}