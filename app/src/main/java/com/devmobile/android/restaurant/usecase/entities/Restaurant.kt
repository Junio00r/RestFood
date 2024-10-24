package com.devmobile.android.restaurant.usecase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.model.datasource.local.entities.Food

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = false) val id: Long,
    var name: String,
    var street: String,
    var city: String,
    var postalCode: String,
)