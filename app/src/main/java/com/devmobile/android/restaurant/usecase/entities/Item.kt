package com.devmobile.android.restaurant.model.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.usecase.entities.Restaurant

// Versão para testes offlines
@Entity(
    tableName = "foods",
    foreignKeys = [
        ForeignKey(
            entity = Restaurant::class,
            parentColumns = ["id"],
            childColumns = ["restaurantId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["restaurantId"])
    ]
)
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val restaurantId: Long,
    val name: String,
    var price: Float,
    val section: String,
    val requiredSides: List<Long>?,
    @ColumnInfo(name = "image_id") val imageId: Int,
    @ColumnInfo(name = "time_for_prepare") val timeForPrepare: String,
    val description: String?
)
