package com.restaurants.viewmodel

import androidx.lifecycle.*
import com.restaurants.model.Menus
import com.restaurants.model.Restaurants
import com.restaurants.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(private val repository: RestaurantRepository) : ViewModel() {

    private val _restaurantsListLiveData = MutableLiveData<List<Restaurants>>()
    val restaurantsListLiveData : LiveData<List<Restaurants>> = _restaurantsListLiveData
    private val menusListLiveData = MutableLiveData<List<Menus>>()
    private val queryLiveData = MutableLiveData<String>()

    companion object {
        private val TAG = RestaurantViewModel::class.simpleName
    }

    val transformation = Transformations.switchMap(queryLiveData) {
        queryLiveData.value?.let {
            searchResults(it)
        }
    }

    private fun searchResults(search: String): LiveData<List<Restaurants?>> {
        val _filterRestaurants = MutableLiveData<List<Restaurants?>>()
        viewModelScope.launch {
            val searchingJob = async {
                searchRestaurants(search)
            }
            val searchMenuJob = async {
                searchMenu(search, this)
            }
            val plus = searchingJob.await().plus(searchMenuJob.await()).toSet().toList()
            _filterRestaurants.postValue(plus)
        }
        return _filterRestaurants
    }

    private suspend fun searchMenu(search: String, scope: CoroutineScope): List<Restaurants?> {
        val restaurantsList = mutableSetOf<Restaurants?>()
        menusListLiveData.value?.forEach { menus ->
            if (menus.categories.isNotEmpty()) {
                val jobArray = arrayOfNulls<Deferred<List<Restaurants?>?>>(menus.categories.size)
                repeat(menus.categories.size) { pos ->
                    jobArray[pos] = scope.async {
                        menus.categories[pos].menuItems?.asSequence()?.filter {
                            it.name.contains(search)
                        }?.map {
                            _restaurantsListLiveData.value?.first { res ->
                                menus.restaurantId == res.id
                            }
                        }?.toList() ?: emptyList()
                    }
                }
                jobArray.forEach {
                    val fetch = it?.await()
                    if (fetch != null && fetch.isNotEmpty()) {
                        restaurantsList += fetch
                    }
                }
            }
        }
        return restaurantsList.toList()
    }

    private fun searchRestaurants(search: String): List<Restaurants> {
        return _restaurantsListLiveData.value?.filter {
            (it.name?.contains(search) ?: false) || (it.cuisine_type?.contains(search) ?: false)
        } ?: emptyList()
    }

    private fun parseRestaurantJson(jsonString: String?): List<Restaurants> {
        return repository.parseRestaurantJson(jsonString)
    }

    private fun parseMenusJson(jsonString: String?): List<Menus> {
        return repository.parseMenusJson(jsonString)
    }

    fun getRestaurantFilterData(jsonString: String?, menuJsonString: String?) {
        viewModelScope.launch {
            val restaurantJob = async {
                parseRestaurantJson(jsonString)
            }

            val menusJob = async {
                parseMenusJson(menuJsonString)
            }
            _restaurantsListLiveData.postValue(restaurantJob.await())
            menusListLiveData.postValue(menusJob.await())
        }
    }

    fun searchQuery(search: String) {
        queryLiveData.postValue(search)
    }

}