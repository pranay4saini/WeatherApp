package com.pranay.weatherapp.ui.screens

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.pranay.weatherapp.R
import com.pranay.weatherapp.ui.location.GetCurrentLocation
import com.pranay.weatherapp.ui.location.getLocationName
import com.pranay.weatherapp.ui.theme.poppinsFamily
import com.pranay.weatherapp.ui.viewmodel.ForecastViewModel
import com.pranay.weatherapp.ui.viewmodel.MainViewModel
import com.pranay.weatherapp.ui.widgets.NavBar
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(
    mainViewModel: MainViewModel,
    forecastViewModel: ForecastViewModel,
    context: Context,
    city : String?,
    navController: NavController
) {
    Log.d("City","$city")
    var latitude : MutableState<Double>
    var longitude : MutableState<Double>

    if (city == "Default") {
        latitude = remember {
            mutableStateOf(360.0)
        }
        longitude = remember {
            mutableStateOf(360.0)
        }
    } else {
        val address = city?.let { getLatLon(context,it) }
        if (address!= null) {
            latitude = remember {
                mutableStateOf(address.latitude)
            }
            longitude = remember {
                mutableStateOf(address.longitude)
            }
        } else {
            latitude = remember {
                mutableStateOf(360.0)
            }
            longitude = remember {
                mutableStateOf(360.0)
            }

            Toast.makeText(context,"UNKNOWN LOCATION", Toast.LENGTH_LONG).show()
        }
    }

    val gradientColors = listOf(Color(0xFF060620), colors.primary)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
    ) {
        Scaffold(content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                HomeElements(
                    mainViewModel = mainViewModel,
                    forecastViewModel = forecastViewModel,
                    context = context,
                    latitude = latitude,
                    longitude = longitude,
                )
            }
        }, bottomBar = {
            NavBar(navController)
        }, containerColor = Color.Transparent)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeElements(
    mainViewModel: MainViewModel,
    forecastViewModel: ForecastViewModel,
    context: Context,
    latitude: MutableState<Double>,
    longitude: MutableState<Double>,
) {

    var locationName by remember {
        mutableStateOf("")
    }
    // cancelled when the composition is disposed
    val scope = rememberCoroutineScope()
    if (latitude.value != 360.0 && longitude.value != 360.0) {
        LaunchedEffect(latitude, longitude) {
            scope.launch {
                locationName = try {
                    getLocationName(context, latitude, longitude)
                } catch (e: Exception) {
                    ""
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 10.dp), horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Outlined.LocationOn,
            contentDescription = stringResource(R.string.location_icon),
            tint = colors.secondary
        )
        Text(
            locationName,
            fontSize = 16.sp,
            fontFamily = poppinsFamily
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp), horizontalArrangement = Arrangement.Start
    ) {
        Text(
            stringResource(R.string.today_report),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFamily
        )
    }
    GetCurrentLocation(
        mainViewModel = mainViewModel,
        forecastViewModel = forecastViewModel,
        context = context,
        latitude = latitude,
        longitude = longitude
    )
}

fun getLatLon(context: Context, city: String) : Address? {
    val geoCoder = Geocoder(context)
    return try {
        val addresses = geoCoder.getFromLocationName(city,1)
        addresses!![0]
    } catch (e: Exception) {
        null
    }
}

