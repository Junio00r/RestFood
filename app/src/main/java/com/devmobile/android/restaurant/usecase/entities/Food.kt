package com.devmobile.android.restaurant.model.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.usecase.enums.FoodSection
import com.devmobile.android.restaurant.usecase.enums.TempoPreparo

// Versão para testes offlines
@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = false) val mId: Long,
    @ColumnInfo(name = "name") var mName: String,
    @ColumnInfo(name = "foodPrice") var mFoodPrice: Float,
    @ColumnInfo(name = "section") var mSection: FoodSection,
    @ColumnInfo(name = "imageId") var mImageId: Int,
    @ColumnInfo(name = "timeIconId") var mTimeIconId: Int,
    @ColumnInfo(name = "timeToPrepare") var mTimeToPrepare: TempoPreparo,
    @ColumnInfo(name = "description") var mDescription: String? = "Food"
)
