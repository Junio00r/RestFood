package com.devmobile.android.restaurant.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Versão para testes offlines
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "sex") val sex: String,
    @ColumnInfo(name = "birthDate") val birthDate: String
)