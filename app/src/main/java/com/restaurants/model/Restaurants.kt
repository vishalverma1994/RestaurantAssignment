package com.restaurants.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Restaurants(
    @PrimaryKey(autoGenerate = false)
    var id: Long = -1,
    var name: String = "",
    var neighborhood: String = "",
    var photograph: String = "",
    var address: String = "",
    var latlng: LatLng? = null,
    var cuisine_type: String = "",
    var operating_hours: OperatingHours? = null,
    var reviews: List<Reviews>? = null
)