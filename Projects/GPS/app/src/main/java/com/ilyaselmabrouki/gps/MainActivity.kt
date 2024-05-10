package com.ilyaselmabrouki.gps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ilyaselmabrouki.gps.ui.theme.GPSTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register activity result launcher for location permission
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Location permission granted, initiate image capture
                takePictureLauncher.launch(createImageUri())
            } else {
                // Location permission denied, handle accordingly
            }
        }

        // Register activity result launcher for taking picture
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                // Picture taken successfully, fetch location
                val imageUri = createImageUri() // Create a new URI for the captured image
                fetchLocationAndDisplayImage(imageUri)
            } else {
                // Picture not taken, handle accordingly (e.g., show an error message)
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

    // Function to create a temporary file URI for storing the captured image
    private fun createImageUri(): Uri {
        val context = applicationContext
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(null)
        return Uri.Builder()
            .scheme("file")
            .authority(context.packageName)
            .appendPath("pictures")
            .appendPath("JPEG_${timeStamp}.jpg")
            .build()
    }

    private fun fetchLocationAndDisplayImage(imageUri: Uri) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        // Location fetched successfully, now you can display the image and location
                        displayImageAndLocation(imageUri, location)
                    } else {
                        // Location not available, handle accordingly
                        displayImageOnly(imageUri)
                    }
                }
                .addOnFailureListener { exception ->
                    // Failed to fetch location, handle the failure
                    displayImageOnly(imageUri)
                }
        } catch (securityException: SecurityException) {
            // Handle SecurityException
            displayImageOnly(imageUri)
        }
    }

    private fun displayImageAndLocation(imageUri: Uri, location: android.location.Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        val locationText = "Latitude: $latitude, Longitude: $longitude"

        // Example: Displaying the image and location using Jetpack Compose
        setContent {
            Column {
                LoadImageFromUri(imageUri = imageUri)
                Text(locationText)
            }
        }
    }

    private fun displayImageOnly(imageUri: Uri) {
        // Here you can display the captured image without the location information
        // You can use the imageUri to load the image into an ImageView or Image composable
        // For example:
        // 1. Load the image using Glide/Picasso or set the Image composable's content to the imageUri

        // Example: Displaying the image using Jetpack Compose
        setContent {
            LoadImageFromUri(imageUri = imageUri)
        }
    }
}

@Composable
fun LoadImageFromUri(imageUri: Uri) {
    val bitmap: ImageBitmap = loadImageFromFile(imageUri)?.asImageBitmap() ?: return

    Image(
        painter = BitmapPainter(bitmap),
        contentDescription = null // provide content description if needed
    )
}

private fun loadImageFromFile(imageUri: Uri): Bitmap? {
    val file = File(imageUri.path)
    return if (file.exists()) {
        BitmapDrawable.createFromPath(file.absolutePath)?.toBitmap()
    } else {
        null
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

    val takePicture = {
        activityResultLauncher.launch(Manifest.permission.CAMERA)
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

    Button(onClick = { takePicture() }) {
        Text("Take Picture")
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
                    // Location fetched successfully, update the location state
                    val latitude = locationResult.latitude
                    val longitude = locationResult.longitude
                    location.value = "Latitude: $latitude, Longitude: $longitude"
                } else {
                    // Location not available, update the location state with a default message
                    location.value = "Location not available"
                }
            }
    } catch (securityException: SecurityException) {
        // Handle SecurityException
        location.value = "Location permission denied."
    }
}