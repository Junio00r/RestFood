package com.devmobile.android.restaurant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) private val id: Long,
    @ColumnInfo(name = "name") private var name: String
    // Mais ainda será implementado
)