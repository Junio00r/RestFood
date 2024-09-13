package com.devmobile.android.restaurant.model

import android.app.Service

abstract class EmailService : Service() {

    override fun onCreate() {
        super.onCreate()
    }

    fun sendCode(email: String) {

    }
}