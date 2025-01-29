package com.devmobile.android.restaurant.usecase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.model.datasource.local.entities.Item

// Versão para testes offlines
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = false) val orderId: Long,
    @ColumnInfo(name = "restaurantId") val restaurantId: Long,
    @ColumnInfo(name = "date") val orderDate: String,
    @ColumnInfo(name = "hour") val orderHour: String,
    @ColumnInfo(name = "items") val orderItems: ArrayList<Item>,
    @ColumnInfo(name = "userName") val orderUserName: String,
    @ColumnInfo(name = "tableOfOrder") val orderTableNumber: Int
)