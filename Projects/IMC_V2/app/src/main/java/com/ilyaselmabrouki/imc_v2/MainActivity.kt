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

@Composable
fun getImageResourceId(bmi: Float): Int {
    return when {
        bmi < 18.5 -> R.drawable.maigre
        bmi < 24.9 -> R.drawable.normal
        bmi < 29.9 -> R.drawable.surpoids
        else -> R.drawable.obese
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IMC_V2Theme {
        IMCLayout()
    }
}



