package com.restaurants.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.restaurants.databinding.AdapterRestaurantBinding
import com.restaurants.model.Restaurants

class RestaurantAdapter : ListAdapter<Restaurants, RestaurantAdapter.ViewHolder>(MyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    inner class ViewHolder(private val binding: AdapterRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindViews(item: Restaurants?) {
            if (item != null) {
                binding.restaurants = item
                var finalRating = 0f
                item.reviews.forEach {
                    finalRating += it.rating
                }

                finalRating /= item.reviews.size
                binding.ratingBar.rating = finalRating
            }
        }
    }
}

class MyDiffUtil : DiffUtil.ItemCallback<Restaurants>() {
    override fun areItemsTheSame(oldItem: Restaurants, newItem: Restaurants): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Restaurants, newItem: Restaurants): Boolean {
        return oldItem == newItem
    }
}