package com.devmobile.android.restaurant.model.localdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.viewmodel.enums.FoodSection
import com.devmobile.android.restaurant.viewmodel.enums.TempoPreparo

/**
 *
 * Food data class that is stored in Room as "foods" table name
 * @property mId auto id generate for database
 * @property mName foodName
 *
 */
@Entity(tableName = "foods")
data class Food(
    @PrimaryKey(autoGenerate = false) var mId: Long,
    @ColumnInfo(name = "name") var mName: String,
    @ColumnInfo(name = "foodPrice") var mFoodPrice: Float,
    @ColumnInfo(name = "section") var mSection: FoodSection,
    @ColumnInfo(name = "imageId") var mImageId: Int,
    @ColumnInfo(name = "timeIconId") var mTimeIconId: Int,
    @ColumnInfo(name = "timeToPrepare") var mTimeToPrepare: TempoPreparo,
    @ColumnInfo(name = "description") var mDescription: String? = "Food"
)
