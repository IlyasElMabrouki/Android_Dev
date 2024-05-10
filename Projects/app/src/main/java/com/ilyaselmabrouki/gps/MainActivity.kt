package com.ilyaselmabrouki.gps

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ilyaselmabrouki.gps.ui.theme.GPSTheme

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register activity result launcher
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, handle accordingly
            } else {
                // Permission denied, handle accordingly
            }
        }

        setContent {
            GPSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(requestPermissionLauncher)
                }
            }
        }
    }
}
@Composable
fun MainScreen(activityResultLauncher: ActivityResultLauncher<String>) {
    val context = LocalContext.current
    val fusedLocationClient = remember(context) {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val location = remember { mutableStateOf<String>("") }

    val requestPermissionLauncher = remember(activityResultLauncher) {
        activityResultLauncher
    }

    val requestPermission = {
        val permissionCheck = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            fetchLocation(fusedLocationClient, location)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Button(onClick = { requestPermission() }) {
        Text("Get Location")
    }

    Text(location.value)
}

private fun fetchLocation(
    fusedLocationClient: FusedLocationProviderClient,
    location: MutableState<String>
) {
    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { locationResult ->
                if (locationResult != null) {
                    location.value = "Latitude: ${locationResult.latitude}, Longitude: ${locationResult.longitude}"
                } else {
                    location.value = "Location not available"
                }
            }
    } catch (securityException: SecurityException) {
        // Handle SecurityException
        location.value = "Location permission denied."
    }
}