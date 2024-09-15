package com.devmobile.android.restaurant.model.repository.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmobile.android.restaurant.model.entities.Food
import com.devmobile.android.restaurant.model.entities.User

@Database(version = 29, entities = [Food::class, User::class], exportSchema = false)
abstract class RestaurantLocalDatabase : RoomDatabase() {

    abstract fun getFoodDao(): IFoodDao

    abstract fun getUserDao(): IUserDao

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