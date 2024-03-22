package com.pranay.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pranay.weatherapp.model.CurrentWeatherObject
import com.pranay.weatherapp.model.Favourite

@Database(entities = [Favourite::class, CurrentWeatherObject::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}