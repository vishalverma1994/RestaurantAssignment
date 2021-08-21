package com.restaurants.di

import android.app.Application
import androidx.room.Room
import com.restaurants.R
import com.restaurants.db.dao.MenuDao
import com.restaurants.db.dao.RestaurantDao
import com.restaurants.db.database.RestaurantDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRestaurantDB(application: Application): RestaurantDB {
        return Room.databaseBuilder(
            application,
            RestaurantDB::class.java,
            application.getString(R.string.database_name)
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideRestaurantDao(restaurantDB: RestaurantDB): RestaurantDao {
        return restaurantDB.getRestaurantDao()
    }

    @Provides
    @Singleton
    fun provideMenuDao(restaurantDB: RestaurantDB): MenuDao {
        return restaurantDB.getMenusDao()
    }
}