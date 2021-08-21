package com.restaurants.model

data class MenuItems(
    var id: Long = -1,
    var name: String = "",
    var description: String = "",
    var price: String = "",
    var images: List<Any>? = null
)
