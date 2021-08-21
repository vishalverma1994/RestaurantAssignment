package com.restaurants.model


data class Restaurants(
    var id: Int?,
    var name: String?,
    var neighborhood: String?,
    var photograph: String?,
    var address: String?,
    var latlng: LatLng?,
    var cuisine_type: String?,
    var operating_hours: OperatingHours?,
    var reviews: List<Reviews>?
) {
    constructor() : this(-1, "", "", "", "", LatLng(), "", OperatingHours(), mutableListOf())
}