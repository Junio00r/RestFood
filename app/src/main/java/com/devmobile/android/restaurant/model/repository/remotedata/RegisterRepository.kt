package com.devmobile.android.restaurant.model.repository.remotedata

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.LoadState
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class RegisterRepository(private val context: Context) {

    private val _registerProgress = MutableLiveData<LoadState>()
    val registerProgress = _registerProgress

    companion object {
        const val authenticationTag = "Authentication"
    }

    suspend fun createAccount(user: User): Boolean {
        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()

        return withContext(Dispatchers.IO) {

            try {

                userDao.insertUser(user)
                Thread.sleep(5000)

                return@withContext true


            } catch (e: IOException) {

                Log.e("Teste", "Não foi possível inserir o usuario no banco de dados")
            }


            false
        }
    }
}