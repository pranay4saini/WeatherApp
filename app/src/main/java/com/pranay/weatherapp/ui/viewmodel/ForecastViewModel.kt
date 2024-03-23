package com.pranay.weatherapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranay.weatherapp.model.CurrentWeatherObject
import com.pranay.weatherapp.model.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val repository: WeatherDbRepository) : ViewModel() {

  private val _weatherObjectList = MutableStateFlow<List<CurrentWeatherObject>>(emptyList())
  val weatherObjectList = _weatherObjectList.asStateFlow()

  init {
      viewModelScope.launch(Dispatchers.IO) {
        repository.getWeatherObjects().distinctUntilChanged()
          .collect {
            if (it.isEmpty()) {
              Log.d("GetWeatherObjects", "Empty favs")
            } else {
              _weatherObjectList.value = it
              Log.d("weatherObjectList", "${weatherObjectList.value}")
            }
          }
      }
  }

  fun insertCurrentWeatherObject(currentWeatherObject: CurrentWeatherObject) = viewModelScope.launch { repository.insertCurrentWeatherObject(currentWeatherObject) }
}