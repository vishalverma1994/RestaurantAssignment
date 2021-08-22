package com.restaurants.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.restaurants.databinding.ActivityMainBinding
import com.restaurants.model.*
import com.restaurants.ui.fragments.RestaurantsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponents(savedInstanceState)
    }

    private fun initComponents(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            //open restaurant fragment
            supportFragmentManager.beginTransaction()
                .replace(binding.flContainer.id, RestaurantsFragment.newInstance(Bundle())).commit()
        }
    }

}