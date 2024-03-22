package com.pranay.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.pranay.weatherapp.data.WeatherDao
import com.pranay.weatherapp.data.WeatherDatabase
import com.pranay.weatherapp.network.WeatherApi
import com.pranay.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Set up all modules needed across the entire application such as databases, repositories
    /* Create a provider to provide the Once Call API */
    @Provides
    @Singleton
    fun provideOneCallApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            // For retrofit to convert all the JSON gotten here into actual objects
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)

        /* Once this is built and run, it will know to create all the classes that it needs
        to create this dependency
         */
    }

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        ).fallbackToDestructiveMigration().build()
}