package com.ilyaselmabrouki.court_counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    var teamA by remember { mutableIntStateOf(0) }
    var teamB by remember { mutableIntStateOf(0) }

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
                    ColumnTeamA(teamA, { teamA += 3 }, { teamA += 2 }) { teamA += 1 }
                    Spacer(modifier = Modifier.width(16.dp)) // Add space between columns
                    ColumnTeamB(teamB, { teamB += 3 }, { teamB += 2 }) { teamB += 1 }
                }
                Row {
                    Spacer(modifier = Modifier.height(16.dp)) // Add space between the columns and the reset button
                    ResetButton(onResetClick = {
                        teamA = 0
                        teamB = 0
                    })
                }
            }
        }
    }
}

@Composable
fun ColumnTeamA(result: Int, onButton3Click: () -> Unit, onButton2Click: () -> Unit, onButton1Click: () -> Unit){

    val buttonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
    )
    val buttonShape = RoundedCornerShape(0.dp) // Modify border radius here

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Team A")
        Text(text = "$result")
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between the text and buttons
        Button(
            onClick = onButton3Click,
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+3 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = onButton2Click,
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+2 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = onButton1Click,
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+1 Points")
        }
    }
}

@Composable
fun ColumnTeamB(result: Int, onButton3Click: () -> Unit, onButton2Click: () -> Unit, onButton1Click: () -> Unit) {
    val buttonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
    )
    val buttonShape = RoundedCornerShape(0.dp) // Modify border radius here

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Team B")
        Text(text = "$result")
        Spacer(modifier = Modifier.height(16.dp)) // Add spacing between the text and buttons
        Button(
            onClick = onButton3Click,
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+3 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = onButton2Click,
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+2 Points")
        }
        Spacer(modifier = Modifier.height(8.dp)) // Add spacing between buttons
        Button(
            onClick = onButton1Click,
            colors = buttonColors,
            shape = buttonShape
        ) {
            Text(text = "+1 Points")
        }
    }
}

@Composable
fun ResetButton(onResetClick: () -> Unit) {
    val buttonColors = ButtonDefaults.buttonColors(
        contentColor = Color.White,
    )
    val buttonShape = RoundedCornerShape(0.dp) // Modify border radius here

    Button(
        onClick = onResetClick,
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