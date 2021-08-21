package com.restaurants.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.restaurants.R.layout
import com.restaurants.extension.loadJSONFromAsset
import com.restaurants.model.*
import com.restaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName
    private lateinit var restaurantViewModel : RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        initComponents()
    }

    private fun initComponents() {
        restaurantViewModel = ViewModelProvider(this)[RestaurantViewModel::class.java]
        parseRestaurantJson()

        parseMenusJson()
        this.loadJSONFromAsset("restaurants.json")?.let {
            restaurantViewModel.parseRestaurantJson(it)
        }

        this.loadJSONFromAsset("menus.json")?.let {
            restaurantViewModel.parseMenusJson(it)
        }
    }

    private fun parseRestaurantJson() {

    }

    private fun parseMenusJson() {

    }
}