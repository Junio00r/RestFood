package com.devmobile.android.restaurant

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmobile.android.restaurant.dao.FoodDao

@Database(version = 1, entities = [Food::class])
abstract class RestaurantDatabase : RoomDatabase(){

    abstract fun getFoodDao() : FoodDao

    companion object {
        private var instance: RestaurantDatabase? = null

        fun getInstance(context: Context) : RestaurantDatabase {
            return instance ?: createDatabaseInstance(context)
        }

        private fun createDatabaseInstance(context: Context) : RestaurantDatabase {
            instance = Room.databaseBuilder(
                context,
                RestaurantDatabase::class.java, "restaurant-database"
            ).allowMainThreadQueries().build()

            return instance as RestaurantDatabase
        }
    }
}