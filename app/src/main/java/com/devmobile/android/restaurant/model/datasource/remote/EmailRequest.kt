package com.devmobile.android.restaurant.model.datasource.remote

data class EmailRequest(
    val sender: Sender,
    val to: List<To>,
    val htmlContent: String,
    val subject: String,
)

data class Sender(val name: String, val email: String)

data class To(val email: String)

