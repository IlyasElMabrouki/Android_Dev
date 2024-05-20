package com.ilyaselmabrouki.elmabrouki_ilyas_glsid_2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ilyaselmabrouki.elmabrouki_ilyas_glsid_2.ui.theme.DiceGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceGameTheme{
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("DiceGamePrefs", Context.MODE_PRIVATE)
    val storedUsername = sharedPreferences.getString("username", null)
    val storedPassword = sharedPreferences.getString("password", null)
    var authenticated by remember { mutableStateOf(storedUsername?.isNotEmpty() == true && storedPassword?.isNotEmpty() == true) }

    if (authenticated) {
        DiceGameUI(sharedPreferences)
    } else {
        AuthenticationScreen { username, password ->
            sharedPreferences.edit().apply {
                putString("username", username)
                putString("password", password)
                apply()
            }
            authenticated = true
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DiceGameTheme {
        AppContent()
    }
}