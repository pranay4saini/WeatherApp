package com.pranay.weatherapp.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.pranay.weatherapp.data.DataOrException
import com.pranay.weatherapp.model.Weather
import com.pranay.weatherapp.model.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository : WeatherRepository) : ViewModel() {

    private var latitude = mutableStateOf(360.0)
    private var longitude = mutableStateOf(360.0)

    suspend fun getWeatherData(
        lat : Double,
        lon : Double
    ) : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(latQuery = lat, lonQuery = lon)
    }
}