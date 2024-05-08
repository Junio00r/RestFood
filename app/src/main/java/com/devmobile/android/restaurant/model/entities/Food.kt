package com.devmobile.android.restaurant.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.model.enums.FoodSection
import com.devmobile.android.restaurant.model.enums.TempoPreparo

// Versão para testes offlines
@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = false) val mId: Long,
    @ColumnInfo(name = "name") val mName: String,
    @ColumnInfo(name = "foodPrice") val mFoodPrice: Float,
    @ColumnInfo(name = "section") val mSection: FoodSection,
    @ColumnInfo(name = "imageId") val mImageId: Int,
    @ColumnInfo(name = "timeIconId") val mTimeIconId: Int,
    @ColumnInfo(name = "timeToPrepare") val mTimeToPrepare: TempoPreparo,
    @ColumnInfo(name = "description") val mDescription: String? = "Food"
)
