package com.restaurants.model

data class Menus(
    var restaurantId: Int = -1,
    var categories: List<Categories> = emptyList()
)
