package com.ilyaselmabrouki.weatherapi

import android.app.SearchManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ilyaselmabrouki.weatherapi.ui.theme.WeatherAPITheme
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp()
                }
            }
        }
    }

    data class Weather(
        val city: String,
        val temperature: Int,
        val tempMin: Int,
        val tempMax: Int,
        val pressure: Int,
        val humidity: Int,
        val date: String
    )
    @Composable
    fun WeatherApp() {
        var searchText by remember { mutableStateOf("") }
        var weatherInfo by remember { mutableStateOf<Weather?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                onImeActionPerformed = { action, _ ->
                    if (action == ImeAction.Search) {
                        // Perform search action here
                        // For now, let's just show a toast
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Weather info
            weatherInfo?.let { weather ->
                WeatherInfo(weather)
            }
        }
    }

    @Composable
    fun WeatherInfo(weather: Weather) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("City: ${weather.city}")
            Text("Temperature: ${weather.temperature}°C")
            Text("Min Temperature: ${weather.tempMin}°C")
            Text("Max Temperature: ${weather.tempMax}°C")
            Text("Pressure: ${weather.pressure} hPa")
            Text("Humidity: ${weather.humidity}%")
            Text("Date: ${weather.date}")
            // Add any other weather information you want to display
        }
    }
}


@Preview
@Composable
fun PreviewAppBar() {
    WeatherApp()
}