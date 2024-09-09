package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import com.devmobile.android.restaurant.RequestResult
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

class FormRepository(
    private val applicationContext: Context,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
) {
    private val _resultRequestData = MutableStateFlow<RequestResult?>(null)
    val resultRequestData: StateFlow<RequestResult?> = _resultRequestData.asStateFlow()

    suspend fun hasEmailAlreadyRegistered(email: String) : Boolean {
        val userDao = RestaurantLocalDatabase.getInstance(applicationContext).getUserDao()
        var canEmailRegister: Boolean

        withContext(coroutineDispatcher) {

            try {

                canEmailRegister = userDao.amountThisEmailRegistered(email) == 0

                when (canEmailRegister) {

                    true -> { _resultRequestData.value = RequestResult.Success() }

                    false -> { _resultRequestData.value = RequestResult.Error(Exception("Email is invalid or already taken")) }
                }

            } catch (e: IOException) {

                Log.e("RegisterRepository", "IO database exception to verify email: ${e.message}")
                throw e

            } catch (e: SQLiteDatabaseCorruptException) {

                Log.e("RegisterRepository", "Database corrupt: ${e.message}")
                throw e

            } catch (e: SQLiteException) {

                Log.e(
                    "RegisterRepository", "SQL exception while creating account: ${e.message}"
                )
                throw e
            }
        }

        return canEmailRegister
    }
}