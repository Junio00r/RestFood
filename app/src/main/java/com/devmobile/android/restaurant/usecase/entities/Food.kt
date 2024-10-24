package com.devmobile.android.restaurant.model.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.usecase.entities.Restaurant
import com.devmobile.android.restaurant.usecase.enums.FoodSection
import com.devmobile.android.restaurant.usecase.enums.TempoPreparo

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
    ]
)
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val restaurantId: Long,
    val name: String,
    var price: Float,
    val section: FoodSection,
    @ColumnInfo(name = "image_id") val imageId: Int,
    @ColumnInfo(name = "time_for_prepare") val timeForPrepare: TempoPreparo,
    val description: String?
)
