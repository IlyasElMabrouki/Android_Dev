package com.ilyaselmabrouki.elmabrouki_ilyas_glsid_2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun DiceGameUI(sharedPreferences: android.content.SharedPreferences) {
    var diceRoll by remember { mutableStateOf(1) }
    var playerMoney by remember { mutableStateOf(0) }
    var casinoMoney by remember { mutableStateOf(Random.nextInt(10, 100)) }
    var gameOver by remember { mutableStateOf(false) }
    var gameStarted by remember { mutableStateOf(false) }
    var moneyInput by remember { mutableStateOf(TextFieldValue("")) }
    val betAmount = 10 // Fixed bet amount

    if (!gameStarted) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Saisir votre fortune initial:", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = moneyInput,
                onValueChange = { moneyInput = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Fortune Initial") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    playerMoney = moneyInput.text.toIntOrNull() ?: 0
                    gameStarted = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Commencer le jeu")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Fortune du Joueur: $playerMoney", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Fortune du Casino: $casinoMoney", style = MaterialTheme.typography.h6)
            }

            if (gameOver || playerMoney <= 0 || casinoMoney <= 0) {
                Text(
                    text = "Winner is ${winner(playerMoney, casinoMoney)}!",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Player Money: $playerMoney",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Casino Money: $casinoMoney",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Result: $diceRoll", style = MaterialTheme.typography.h6)
                    Image(
                        painter = painterResource(id = getDiceImageResource(diceRoll)),
                        contentDescription = "Dice",
                        modifier = Modifier.size(100.dp) // Reduced size
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Button(
                        onClick = {
                            diceRoll = rollDice()
                            if (diceRoll == 2 || diceRoll == 3) {
                                casinoMoney --
                            } else {
                                playerMoney --
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Roll Dice")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { gameOver = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Finish Game")
                    }
                }
            }
        }
    }
}

fun getDiceImageResource(diceRoll: Int): Int {
    return when (diceRoll) {
        1 -> R.drawable.dice1
        2 -> R.drawable.dice2
        3 -> R.drawable.dice3
        4 -> R.drawable.dice4
        5 -> R.drawable.dice5
        6 -> R.drawable.dice6
        else -> R.drawable.dice1
    }
}

fun winner(playerMoney: Int, casinoMoney: Int): String {
    return when {
        playerMoney > casinoMoney -> "Player"
        playerMoney < casinoMoney -> "Casino"
        else -> "Tie"
    }
}

fun rollDice(): Int {
    return Random.nextInt(1, 7)
}