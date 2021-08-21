package com.restaurants.model

data class Categories(
    var id: Long = -1,
    var name: String = "",
    var menuItems: List<MenuItems>? = null
)
