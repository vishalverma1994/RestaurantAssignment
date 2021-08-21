package com.restaurants.model

import java.math.BigDecimal

data class LatLng(
    var lat: BigDecimal = BigDecimal.ZERO,
    var lng: BigDecimal = BigDecimal.ZERO
)
