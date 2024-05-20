package com.ilyaselmabrouki.elmabrouki_ilyas_glsid_2

import android.content.Context
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

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

@Composable
fun AuthenticationScreen(onAuthenticate: (String, String) -> Unit) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nom d'utilisateur") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onAuthenticate(username.text, password.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Connexion")
        }
    }
}