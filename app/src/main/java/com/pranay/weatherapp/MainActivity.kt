package com.pranay.weatherapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pranay.weatherapp.ui.navigation.WeatherNavigation
import com.pranay.weatherapp.ui.theme.WeatherAppTheme
import com.pranay.weatherapp.ui.theme.poppinsFamily
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnected == true
        if (isConnected) {
            setContent {
                WeatherApp(this)
            }
        } else {
            setContent {
                NoConnection(this)
            }
        }
    }
}

@Composable
fun WeatherApp(context: Context) {
    WeatherAppTheme {
        // A surface container using the 'background' color from the theme
        androidx.compose.material.Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.material.MaterialTheme.colors.primary
        ) {
            WeatherNavigation(context)
        }
    }
}

@Composable
fun NoConnection(context: Context) {
    WeatherAppTheme {
        // A surface container using the 'background' color from the theme
        androidx.compose.material.Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.material.MaterialTheme.colors.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 15.dp, end = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material.Text(
                    "No internet connection detected. Please check your internet connection and try again.",
                    fontFamily = poppinsFamily,
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = {
                        // Check internet connection and refresh app
                        val connectivityManager =
                            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
                        val isConnected: Boolean = activeNetwork?.isConnected == true
                        if (isConnected) {
                            // Update the app's content to display the WeatherApp composable
                            (context as MainActivity).setContent {
                                WeatherApp(context)
                            }
                        } else {
                            // Update the app's content to display the NoConnection composable
                            (context as MainActivity).setContent {
                                NoConnection(context)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.textButtonColors(containerColor = Color(0xFFd68118)),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    androidx.compose.material.Text(
                        "Try Again",
                        fontFamily = poppinsFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )

                }

            }
        }
    }
}