package com.restaurants.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.restaurants.db.dao.MenuDao
import com.restaurants.db.dao.RestaurantDao
import com.restaurants.model.Menus
import com.restaurants.model.Restaurants

@Database(entities = [Restaurants::class, Menus::class], version = 1, exportSchema = false)
abstract class RestaurantDB : RoomDatabase() {

    abstract fun getRestaurantDao(): RestaurantDao
    abstract fun getMenusDao(): MenuDao
}