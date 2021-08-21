package com.restaurants.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.restaurants.model.Restaurants

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRestaurants(restaurantList: List<Restaurants>)

    @Query("SELECT * FROM restaurants")
    fun getAllRestaurants(): LiveData<List<Restaurants>>
}