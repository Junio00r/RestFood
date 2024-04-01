package com.devmobile.android.restaurant

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmobile.android.restaurant.dao.FoodDao
import com.devmobile.android.restaurant.dao.UserDao

@Database(version = 17, entities = [Food::class, User::class], exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun getFoodDao(): FoodDao

    abstract fun getUserDao(): UserDao

    companion object {

        private var instance: RestaurantDatabase? = null

        fun getInstance(context: Context): RestaurantDatabase {

            return instance ?: createDatabaseInstance(context)
        }

        private fun createDatabaseInstance(context: Context): RestaurantDatabase {
            return Room.databaseBuilder(
                context,
                RestaurantDatabase::class.java, "restaurant-database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                .also { instance = it }
        }
    }
}