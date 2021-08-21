package com.restaurants.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.restaurants.model.*
import com.restaurants.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(val repository: RestaurantRepository) : ViewModel() {

    private val restaurantList = mutableListOf<Restaurants>()
    private val menusList = mutableListOf<Menus>()

    companion object {
        private val TAG = RestaurantViewModel::class.simpleName
    }

    fun parseRestaurantJson(jsonString: String) {
        try {
            val jsonObject = JSONObject(jsonString)
            val restaurantsArray: JSONArray = jsonObject.getJSONArray("restaurants")
            for (i in 0 until restaurantsArray.length()) {
                val restaurants = Restaurants()
                restaurants.id = restaurantsArray.optJSONObject(i).optLong("id")
                restaurants.neighborhood = restaurantsArray.optJSONObject(i).optString("neighborhood")
                restaurants.photograph = restaurantsArray.optJSONObject(i).optString("photograph")
                restaurants.address = restaurantsArray.optJSONObject(i).optString("address")
                val latLng = LatLng()
                val latLngJsonObject = restaurantsArray.optJSONObject(i).getJSONObject("latlng")
                latLng.lat = latLngJsonObject.optDouble("lat").toBigDecimal()
                latLng.lng = latLngJsonObject.optDouble("lng").toBigDecimal()
                restaurants.latlng = latLng
                restaurants.cuisine_type = restaurantsArray.optJSONObject(i).optString("cuisine_type")
                val operatingHours = OperatingHours()
                val operatingHoursJsonObject = restaurantsArray.optJSONObject(i).getJSONObject("operating_hours")
                operatingHours.Monday = operatingHoursJsonObject.optString("Monday")
                operatingHours.Tuesday = operatingHoursJsonObject.optString("Tuesday")
                operatingHours.Wednesday = operatingHoursJsonObject.optString("Wednesday")
                operatingHours.Thursday = operatingHoursJsonObject.optString("Thursday")
                operatingHours.Friday = operatingHoursJsonObject.optString("Friday")
                operatingHours.Saturday = operatingHoursJsonObject.optString("Saturday")
                operatingHours.Sunday = operatingHoursJsonObject.optString("Sunday")
                restaurants.operating_hours = operatingHours
                val reviewList = mutableListOf<Reviews>()
                val reviewsJsonArray = restaurantsArray.optJSONObject(i).getJSONArray("reviews")
                for (j in 0 until reviewsJsonArray.length()) {
                    val reviews = Reviews()
                    reviews.name = reviewsJsonArray.optJSONObject(j).optString("name")
                    reviews.date = reviewsJsonArray.optJSONObject(j).optString("date")
                    reviews.rating = reviewsJsonArray.optJSONObject(j).optInt("rating")
                    reviews.comments = reviewsJsonArray.optJSONObject(j).optString("comments")
                    reviewList.add(reviews)
                }
                restaurants.reviews = reviewList
                restaurantList.add(restaurants)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        repository.insertRestaurantsIntoDB(restaurantList)
        Log.e(TAG, "Restaurant Data : $restaurantList")
    }

    fun parseMenusJson(jsonString: String) {
        try {
            val jsonObject = JSONObject(jsonString)
            val menusArray: JSONArray = jsonObject.getJSONArray("menus")
            for (i in 0 until menusArray.length()) {
                val menus = Menus()
                menus.restaurantId = menusArray.optJSONObject(i).optLong("restaurantId")
                val categoriesList = mutableListOf<Categories>()
                val categoriesJsonArray = menusArray.optJSONObject(i).getJSONArray("categories")
                for (j in 0 until categoriesJsonArray.length()) {
                    val categories = Categories()
                    categories.id = categoriesJsonArray.optJSONObject(j).optLong("id")
                    categories.name = categoriesJsonArray.optJSONObject(j).optString("name")
                    val menuItemsList = mutableListOf<MenuItems>()
                    val menuItemsJsonArray = categoriesJsonArray.optJSONObject(i).getJSONArray("menu-items")
                    for (k in 0 until menuItemsJsonArray.length()) {
                        val menuItems = MenuItems()
                        menuItems.id = menuItemsJsonArray.optJSONObject(k).optLong("id")
                        menuItems.name = menuItemsJsonArray.optJSONObject(k).optString("name")
                        menuItems.description = menuItemsJsonArray.optJSONObject(k).optString("description")
                        menuItems.price = menuItemsJsonArray.optJSONObject(k).optString("price")
                        val imageList = mutableListOf<Any>()
                        if (menuItemsJsonArray.optJSONObject(k).has("images")) {
                            val imageJsonArray = menuItemsJsonArray.optJSONObject(k).getJSONArray("images")
                            for (l in 0 until imageJsonArray.length()) {
                                imageList.add(imageJsonArray.optJSONObject(l))
                            }
                        }
                        menuItemsList.add(menuItems)
                        menuItems.images = imageList
                    }
                    categories.menuItems = menuItemsList
                    categoriesList.add(categories)
                }
                menus.categories = categoriesList
                menusList.add(menus)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        repository.insertMenusIntoDB(menusList)
        Log.e(TAG, "Menus Data : $menusList")
    }
}