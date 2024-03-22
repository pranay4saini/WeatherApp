package com.pranay.weatherapp.network

import com.pranay.weatherapp.model.Weather
import com.pranay.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "metric",
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appid: String = Constants.API_KEY
    ): Weather
}