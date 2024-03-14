package com.ilyaselmabrouki.imc_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyaselmabrouki.imc_v2.ui.theme.IMC_V2Theme

//This is the entry point of the Android application.
//It sets the content view to IMCLayout() using Jetpack Compose.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMC_V2Theme {
                IMCLayout()
            }
        }
    }
}

//This composable function represents the main layout of the IMC (BMI) calculator.
//It initializes three state variables: weight, height, and result, using mutableFloatStateOf.
//Inside Surface, it lays out the UI components using Column.
//It includes ImageWithTextLayout, TwoTextBoxLayout, and ButtonTextImageLayout composable functions.
@Composable
fun IMCLayout(){
    var weight by remember { mutableFloatStateOf(0F) }
    var height by remember { mutableFloatStateOf(0F) }
    var result by remember { mutableFloatStateOf(0F) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ImageWithTextLayout()
            TwoTextBoxLayout(
                weight = weight,
                height = height,
                onWeightChange = { newWeight ->
                    weight = newWeight
                },
                onHeightChange = { newHeight ->
                    height = newHeight
                }
            )
            ButtonTextImageLayout(result,
                onButtonClick = {
                    var height_cm = height / 100;
                    result = weight / (height_cm * height_cm)
                }
            )
        }
    }
}

//This composable function renders two text fields for entering weight and height.
//It receives weight and height values and functions to handle their changes.
//Each text field is wrapped inside a Column, along with corresponding text labels.
@Composable
fun TwoTextBoxLayout(weight: Float, height: Float, onWeightChange: (Float) -> Unit, onHeightChange: (Float) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Enter your weight")
        TextField(
            value = weight.toString(),
            onValueChange = { newWeight ->
                // Convert the string to a float and then pass it to the callback
                onWeightChange(newWeight.toFloatOrNull() ?: 0f)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(text = "Enter your height")
        TextField(
            value = height.toString(),
            onValueChange = { newHeight ->
                // Convert the string to a float and then pass it to the callback
                onHeightChange(newHeight.toFloatOrNull() ?: 0f)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

//This composable function displays an image with text.
//It shows an image using Image composable.
@Composable
fun ImageWithTextLayout() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.imc),
            contentDescription = "Your Image",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

//This composable function renders a button, text, and image.
//It receives the result of BMI calculation and a callback function onButtonClick for handling button click.
//If the result is non-zero, it displays the BMI result along with an image representing BMI category.
@Composable
fun ButtonTextImageLayout(result: Float, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calculate IMC")
        }

        if (result != 0f) {
            val imageResId = getImageResourceId(result)
            Text(text = "Your BMI is: $result")
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Your Image"
            )
        }
    }
}

//This function maps BMI values to corresponding drawable resources (images).
@Composable
fun getImageResourceId(bmi: Float): Int {
    return when {
        bmi < 18.5 -> R.drawable.maigre
        bmi < 24.9 -> R.drawable.normal
        bmi < 29.9 -> R.drawable.surpoids
        else -> R.drawable.obese
    }
}

//This is a preview function for the layout. It's used for UI previewing during development.
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IMC_V2Theme {
        IMCLayout()
    }
}



