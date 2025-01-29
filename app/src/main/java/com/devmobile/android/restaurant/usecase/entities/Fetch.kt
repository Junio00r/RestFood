package com.devmobile.android.restaurant.usecase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fetches")
data class Fetch(
    @PrimaryKey(autoGenerate = false) val fetchName: String
)
