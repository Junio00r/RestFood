package com.devmobile.android.restaurant.model.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmobile.android.restaurant.model.Food
import com.devmobile.android.restaurant.model.repository.dao.IFoodDao
import com.devmobile.android.restaurant.model.repository.dao.IUserDao
import com.devmobile.android.restaurant.model.User

@Database(version = 18, entities = [Food::class, User::class], exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun getFoodDao(): IFoodDao

    abstract fun getUserDao(): IUserDao

    companion object {

        private var _instance: RestaurantDatabase? = null

        fun getInstance(context: Context): RestaurantDatabase {

            return _instance ?: createDatabaseInstance(context)
        }

        private fun createDatabaseInstance(context: Context): RestaurantDatabase {
            return Room.databaseBuilder(
                context,
                RestaurantDatabase::class.java, "restaurant-database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                .also { _instance = it }
        }
    }
}