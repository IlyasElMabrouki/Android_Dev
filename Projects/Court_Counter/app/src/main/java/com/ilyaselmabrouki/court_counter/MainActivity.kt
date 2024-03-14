package com.ilyaselmabrouki.court_counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaselmabrouki.court_counter.ui.theme.Court_CounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Court_CounterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Court_CounterApp()
                }
            }
        }
    }
}

@Composable
fun Court_CounterApp() {
    Court_CounterTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center, // Center the Row vertically
                horizontalAlignment = Alignment.CenterHorizontally, // Center the Row horizontally
            ) {
                Row(
                    modifier = Modifier.padding(16.dp)
                ) {
                    ColumnTeamA()
                    Spacer(modifier = Modifier.width(16.dp)) // Add space between columns
                    ColumnTeamB()
                }
                Row {
                    Spacer(modifier = Modifier.height(16.dp)) // Add space between the columns and the reset button
                    ResetButton()
                }
            }
        }
    }
}

@Composable
fun ColumnTeamA(){
    val buttonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
    )
    val buttonShape = RoundedCornerShape(0.dp) // Modify border radius here

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Team A")
        Text(text = "0")
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between the text and buttons
        Button(
            onClick = { /*TODO*/ },
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+3 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = { /*TODO*/ },
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+2 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = { /*TODO*/ },
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+1 Points")
        }
    }
}

@Composable
fun ColumnTeamB(){
    val buttonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
    )
    val buttonShape = RoundedCornerShape(0.dp) // Modify border radius here

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Team B")
        Text(text = "0")
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between the text and buttons
        Button(
            onClick = { /*TODO*/ },
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+3 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = { /*TODO*/ },
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+2 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = { /*TODO*/ },
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+1 Points")
        }
    }
}

@Composable
fun ResetButton(){
    val buttonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
    )
    val buttonShape = RoundedCornerShape(0.dp) // Modify border radius here

    Button(
        onClick = { /*TODO*/ },
        colors = buttonColors,
        shape = buttonShape
    ) {
        Text(text = "Reset")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Court_CounterTheme {
        Court_CounterApp()
    }
}