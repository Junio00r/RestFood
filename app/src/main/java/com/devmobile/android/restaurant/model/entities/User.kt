package com.devmobile.android.restaurant.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Versão para testes offlines
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "lastname") var lastname: String?,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
)