package com.devmobile.android.restaurant.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Versão para testes offlines
// Ainda irei implementar algum tipo de builder pattern
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "lastname") var lastname: String?,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String,
)