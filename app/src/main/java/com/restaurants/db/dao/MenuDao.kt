package com.restaurants.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.restaurants.model.Menus

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRestaurantMenu(restaurantMenuList: List<Menus>)

    @Query("SELECT * FROM menus")
    fun getAllRestaurantMenus(): LiveData<List<Menus>>

    @Query("SELECT * FROM menus WHERE restaurantId = :restaurantId")
    fun getAllRestaurantMenus(restaurantId: Long): LiveData<List<Menus>>
}