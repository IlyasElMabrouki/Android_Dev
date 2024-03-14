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
import androidx.compose.runtime.saveable.rememberSaveable
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

data class TeamScore(var score: Int = 0)

@Composable
fun Court_CounterApp() {
    var teamA by rememberSaveable { mutableIntStateOf(0) }
    var teamB by rememberSaveable { mutableIntStateOf(0) }

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
                    TeamColumn(teamA, "Team A") {
                        teamA+= it
                    }
                    Spacer(modifier = Modifier.width(16.dp)) // Add space between columns
                    TeamColumn(teamB, "Team B") {
                        teamB += it
                    }
                }
                Row {
                    Spacer(modifier = Modifier.height(16.dp)) // Add space between the columns and the reset button
                    ResetButton {
                        teamA = 0
                        teamB = 0
                    }
                }
            }
        }
    }
}

@Composable
fun TeamColumn(teamScore: Int, teamName: String, onScoreChange: (Int) -> Unit) {
    val buttonColors = ButtonDefaults.buttonColors(contentColor = Color.White)
    val buttonShape = RoundedCornerShape(0.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = teamName)
        Text(text = "${teamScore}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onScoreChange(3) }, colors = buttonColors, shape = buttonShape) {
            Text(text = "+3 Points")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onScoreChange(2) }, colors = buttonColors, shape = buttonShape) {
            Text(text = "+2 Points")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onScoreChange(1) }, colors = buttonColors, shape = buttonShape) {
            Text(text = "+1 Point")
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