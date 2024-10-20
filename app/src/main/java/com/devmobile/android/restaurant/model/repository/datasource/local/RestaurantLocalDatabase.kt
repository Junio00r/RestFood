package com.devmobile.android.restaurant.model.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmobile.android.restaurant.model.datasource.local.entities.Food
import com.devmobile.android.restaurant.model.datasource.local.entities.User
import com.devmobile.android.restaurant.model.repository.datasource.local.IRestaurantDao
import com.devmobile.android.restaurant.usecase.entities.Restaurant

@Database(
    version = 30,
    entities = [Food::class, User::class, Restaurant::class],
    exportSchema = false
)
abstract class RestaurantLocalDatabase : RoomDatabase() {

    abstract fun getFoodDao(): IFoodDao

    abstract fun getUserDao(): IUserDao

    abstract fun getRestaurantDao(): IRestaurantDao

    companion object {

        private var _instance: RestaurantLocalDatabase? = null

        @Synchronized
        fun getInstance(context: Context): RestaurantLocalDatabase {

            return _instance ?: createDatabaseInstance(context)
        }

        private fun createDatabaseInstance(context: Context): RestaurantLocalDatabase {
            return Room.databaseBuilder(
                context,
                RestaurantLocalDatabase::class.java, "restaurant-database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                .also { _instance = it }
        }
    }
}