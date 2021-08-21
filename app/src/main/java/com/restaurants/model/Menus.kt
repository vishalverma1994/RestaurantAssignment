package com.restaurants.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menus")
data class Menus(
    @PrimaryKey(autoGenerate = false)
    var restaurantId: Long = -1,
    var categories: List<Categories>? = null
)
