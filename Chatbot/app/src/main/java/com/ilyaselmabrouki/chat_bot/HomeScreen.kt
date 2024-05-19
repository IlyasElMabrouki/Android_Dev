package com.ilyaselmabrouki.chat_bot

import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent


@Composable
fun AppContent(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val appUiState = viewModel.uiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    HomeScreen(uiState = appUiState.value) { inputText ->

        coroutineScope.launch {
            viewModel.questioning(userInput = inputText, selectedImages = listOf())
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState = HomeUiState.Loading,
    onSendClicked: (String) -> Unit
) {

    var userQues by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Gemini AI Chatbot") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Column {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    //Input Field
                    OutlinedTextField(
                        value = userQues,
                        onValueChange = {
                            userQues = it
                        },
                        label = { Text(text = "User Input") },
                        placeholder = { Text(text = "Ask question") },
                        modifier = Modifier.fillMaxWidth(0.83f)
                    )

                    //Send Button
                    IconButton(
                        onClick = {
                            if (userQues.isNotBlank()) {
                                onSendClicked(userQues)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            }
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (uiState) {
                is HomeUiState.Initial -> {}
                is HomeUiState.Loading -> {
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is HomeUiState.Success -> {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(), shape = MaterialTheme.shapes.large
                    ) {
                        Text(text = uiState.outputText, modifier = Modifier.padding(16.dp))
                    }
                }

                is HomeUiState.Error -> {
                    Card(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                    ) {
                        Text(text = uiState.error)
                    }
                }
            }

        }

    }

}