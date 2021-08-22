package com.restaurants.model


data class Restaurants(
    var id: Int = -1,
    var name: String = "",
    var neighborhood: String = "",
    var photograph: String = "",
    var address: String = "",
    var latlng: LatLng = LatLng(),
    var cuisine_type: String = "",
    var operating_hours: OperatingHours = OperatingHours(),
    var reviews: List<Reviews> = emptyList()
)