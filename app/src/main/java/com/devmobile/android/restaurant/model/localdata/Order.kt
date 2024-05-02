package com.devmobile.android.restaurant.model.localdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = false) val orderId: Long,
    @ColumnInfo(name = "date") val orderDate: String,
    @ColumnInfo(name = "hour") val orderHour: String,
    @ColumnInfo(name = "foods") val orderFoods: ArrayList<Food>,
    @ColumnInfo(name = "userName") val orderUserName: String,
    @ColumnInfo(name = "tableOfOrder") val orderTable: String
)