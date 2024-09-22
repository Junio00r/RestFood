package com.devmobile.android.restaurant.model.repository.authentication

import android.content.Context
import android.net.DhcpInfo
import android.net.IpSecManager.UdpEncapsulationSocket
import android.net.http.UploadDataProvider
import android.util.Log
import com.devmobile.android.restaurant.model.repository.datasource.remote.EmailApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody

class TokenVerificationRepository(
    private val context: Context,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val emailCommunicationHandler: EmailApiService,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher),
) {

    fun requestNewVerificationCode(email: String) {

        // Uses email api to send verification code
        coroutineScope.launch {

            val result =
                emailCommunicationHandler.emailService.sendEmail(
                    email = email,
                    messageFile =
                )
        }
    }

    suspend fun createAccount() {

        coroutineScope.launch {

            Log.i("Creation", "User Account Created")
        }.join()
    }
}