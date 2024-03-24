package com.pranay.weatherapp.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pranay.weatherapp.ui.screens.ForecastScreen
import com.pranay.weatherapp.ui.screens.SearchScreen
import com.pranay.weatherapp.ui.screens.SettingScreen
import com.pranay.weatherapp.ui.screens.SplashScreen
import com.pranay.weatherapp.ui.screens.WeatherScreen
import com.pranay.weatherapp.ui.viewmodel.FavouriteViewModel
import com.pranay.weatherapp.ui.viewmodel.ForecastViewModel
import com.pranay.weatherapp.ui.viewmodel.MainViewModel
import com.pranay.weatherapp.ui.widgets.BottomNavItem

@Composable
fun WeatherNavigation(context: Context) {
    val navController = rememberNavController()
    val mainViewModel = hiltViewModel<MainViewModel>()
    val favouriteViewModel = hiltViewModel<FavouriteViewModel>()
    val forecastViewModel = hiltViewModel<ForecastViewModel>()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        val route = BottomNavItem.Home.route
        composable("$route/{city}", arguments = listOf(navArgument(name = "city") {
            type = NavType.StringType
        })) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                WeatherScreen(
                    navController = navController,
                    mainViewModel = mainViewModel,
                    forecastViewModel = forecastViewModel,
                    context = context,
                    city = city
                )
            }
        }
        composable(BottomNavItem.Forecast.route) {
            ForecastScreen(navController = navController, forecastViewModel)
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen(navController = navController, context, favouriteViewModel, mainViewModel)
        }
        composable(BottomNavItem.Settings.route) {
            SettingScreen(navController = navController)
        }
    }
}