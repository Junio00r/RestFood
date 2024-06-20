package com.devmobile.android.restaurant.model.repository.remotedata

import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.devmobile.android.restaurant.AccountException
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RegisterRepository(private val context: Context) {

    suspend fun createAccount(user: User) {
        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()

        return withContext(Dispatchers.IO) {

            try {

                val isNotEmailRegistered = userDao.hasEmailRegistered(user.email) == 0

                if (isNotEmailRegistered) {

                    userDao.insertUser(user)
                } else {

                    throw AccountException()
                }

            } catch (e: AccountException) {

                Log.e(
                    "Teste",
                    "Account creating exception: Email already registered on database"
                )
                throw e

            } catch (e: IOException) {

                Log.e(
                    "Test RegisterRepository",
                    "IO database exception to create account: ${e.message}"
                )
                throw e

            } catch (e: SQLiteDatabaseCorruptException) {

                Log.e(
                    "Test RegisterRepository", "Database corrupt: ${e.message}"
                )
                throw e

            } catch (e: SQLiteException) {

                Log.e(
                    "Test RegisterRepository", "SQL exception while creating account: ${e.message}"
                )
                throw e

            } catch (e: Exception) {

                Log.e(
                    "Test RegisterRepository",
                    "Unexpected exception while creating account: ${e.message}"
                )
                throw e
            }
        }
    }
}