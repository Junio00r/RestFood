package com.devmobile.android.restaurant.usecase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "street") var street: String,
    @ColumnInfo(name = "city") var city: String,
    @ColumnInfo(name = "postal_code") var postalCode: String,
)