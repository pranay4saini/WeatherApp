package com.pranay.weatherapp.model.repository

import com.pranay.weatherapp.data.DataOrException
import com.pranay.weatherapp.model.Weather
import com.pranay.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api : WeatherApi) {

    suspend fun getWeather(
        latQuery : Double,
        lonQuery : Double
    ): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(lat = latQuery, lon = lonQuery)
        } catch (e : Exception) {
            return DataOrException(e = e)
        }
        return DataOrException(data = response)
    }
}