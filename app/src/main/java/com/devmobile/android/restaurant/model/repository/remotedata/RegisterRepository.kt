package com.devmobile.android.restaurant.model.repository.remotedata

import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.util.Log
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.sql.SQLTimeoutException

class RegisterRepository(private val context: Context) {

    suspend fun createAccount(user: User): Boolean {
        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()

        return withContext(Dispatchers.IO) {

        try {

                userDao.insertUser(user)

                delay(5000)
                return@withContext true


            } catch (e: IOException) {

                Log.e("Teste", "Não foi possível inserir o usuario no banco de dados")
            } catch (e: SQLTimeoutException) {
                Log.e("Teste", "Error Creating Account: ${e.message}")
            } catch (e: SQLiteDatabaseCorruptException) {
                Log.e("Teste", "Error Creating Account: ${e.message}")
            }

            return@withContext false
        }
    }
}