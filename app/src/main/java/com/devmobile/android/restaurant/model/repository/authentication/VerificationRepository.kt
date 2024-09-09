package com.devmobile.android.restaurant.model.repository.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmobile.android.restaurant.AccountException
import com.devmobile.android.restaurant.model.entities.User
import com.devmobile.android.restaurant.model.repository.localdata.RestaurantLocalDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random

class VerificationRepository(
    private val context: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher),
) {
    private val codeGenerator: Random = Random.Default
    private var currentCodeGenerated: String = ""

    init {

        coroutineScope.launch {

            requestNewVerificationCode()
        }
    }

    @SuppressLint("DefaultLocale")
    suspend fun requestNewVerificationCode() {

        val codeGenerated = codeGenerator.nextInt(0, 1_000_000)
        currentCodeGenerated = String.format("%06d", codeGenerated)

        withContext(Dispatchers.IO) {

            // Uses email api to send verification code

            delay(6000).let {

                // Simulation time for request code
            }
        }

        Log.i("REQUEST", "Request New Verification Code")
    }

    private suspend fun createAccount(user: User) {

        val userDao = RestaurantLocalDatabase.getInstance(context).getUserDao()

        return withContext(defaultDispatcher) {

            try {

                userDao.insertUser(user)

            } catch (e: IOException) {

                throw e

            } catch (e: SQLiteDatabaseCorruptException) {

                throw e

            } catch (e: SQLiteException) {

                throw e

            } catch (e: AccountException) {

                throw e
            }
        }
    }

    fun checkCode(codeEntered: String): Boolean {

        return codeEntered == currentCodeGenerated
    }
}