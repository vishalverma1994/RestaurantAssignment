package com.restaurants.repository

import com.restaurants.db.dao.MenuDao
import com.restaurants.db.dao.RestaurantDao
import com.restaurants.model.Menus
import com.restaurants.model.Restaurants
import javax.inject.Inject

class RestaurantRepository @Inject constructor(private val restaurantDao : RestaurantDao, private val menuDao: MenuDao){

    fun insertRestaurantsIntoDB(restaurantList:List<Restaurants>) {
        restaurantDao.insertRestaurants(restaurantList)
    }

    fun insertMenusIntoDB(menusList: List<Menus>) {
        menuDao.insertRestaurantMenu(menusList)
    }
}