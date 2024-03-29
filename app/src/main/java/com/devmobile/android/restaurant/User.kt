package com.devmobile.android.restaurant

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false) var id: Long,
    @ColumnInfo(name = "name") var name: String
    // Mais ainda será implementado
)