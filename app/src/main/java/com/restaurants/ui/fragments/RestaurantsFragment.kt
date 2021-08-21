package com.restaurants.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.restaurants.R
import com.restaurants.adapters.RestaurantAdapter
import com.restaurants.databinding.FragmentRestaurantsBinding
import com.restaurants.extension.loadJSONFromAsset
import com.restaurants.model.Menus
import com.restaurants.model.Restaurants
import com.restaurants.viewmodel.RestaurantViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.app.SearchManager
import android.content.Context
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.restaurants.ui.activity.MainActivity


@AndroidEntryPoint
class RestaurantsFragment : Fragment() {

    private lateinit var restaurantViewModel: RestaurantViewModel
    private var restaurantsList = emptyList<Restaurants>()
    private var menusList = emptyList<Menus>()
    private lateinit var restaurantAdapter: RestaurantAdapter
    private lateinit var binding: FragmentRestaurantsBinding
    private lateinit var searchView: SearchView
    private lateinit var queryTextListener: OnQueryTextListener

    companion object {
        private val TAG = RestaurantsFragment::class.simpleName
        fun newInstance(args: Bundle?) =
            RestaurantsFragment().apply {
                arguments = args
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        initComponents()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (::searchView.isInitialized) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryTextListener = object : OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    Log.i("$TAG onQueryTextChange", newText)
                    if (!newText.isNullOrEmpty()){
                        restaurantViewModel.searchQuery(newText)
                    }
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.i("$TAG onQueryTextSubmit", query)
                    return true
                }
            }

            searchView.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->

                return false
            else -> {

            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    private fun initComponents() {
        restaurantViewModel = ViewModelProvider(this)[RestaurantViewModel::class.java]

        restaurantViewModel.getRestaurantFilterData(requireContext().loadJSONFromAsset("restaurants.json"), requireContext().loadJSONFromAsset("menus.json"))

        setRestaurantAdapter()
        observeRestaurantsList()
        observerFilterRestaurants()
    }

    private fun observeRestaurantsList() {
        restaurantViewModel.restaurantsListLiveData.observe(viewLifecycleOwner, {
            restaurantAdapter.submitList(it)
        })
    }

    private fun observerFilterRestaurants() {
        restaurantViewModel.transformation.observe(viewLifecycleOwner, {
            restaurantAdapter.submitList(it)
        })
    }

    private fun setRestaurantAdapter() {
        binding.rvRestaurants.apply {
            layoutManager = LinearLayoutManager(requireContext())
            restaurantAdapter = RestaurantAdapter()
            adapter = restaurantAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
    }
}