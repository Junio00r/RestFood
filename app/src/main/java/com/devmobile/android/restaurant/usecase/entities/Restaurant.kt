package com.devmobile.android.restaurant.usecase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "restaurants",
)
data class Restaurant(
    @PrimaryKey(autoGenerate = false) val id: Long,
    var name: String,
    val sections: List<String>,
    var street: String,
    var city: String,
    var postalCode: String,
)