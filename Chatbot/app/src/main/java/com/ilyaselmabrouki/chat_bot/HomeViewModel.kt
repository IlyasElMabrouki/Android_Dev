package com.ilyaselmabrouki.chat_bot

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Initial)
    val uiState = _uiState.asStateFlow()

    private lateinit var generativeModel: GenerativeModel

    init {
        val config = generationConfig {
            temperature = 0.70f // 0 to 1
        }

        generativeModel = GenerativeModel(
            modelName = "gemini-pro", // Changed to "gemini-pro"
            apiKey = "",
            generationConfig = config
        )
    }

    fun questioning(userInput: String, selectedImages: List<Bitmap>) {
        _uiState.value = HomeUiState.Loading
        val prompt = userInput // Changed to only consider userInput

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = content {
                    text(prompt) // Removed the loop for adding images
                }

                var output = ""
                generativeModel.generateContentStream(content).collect {
                    output += it.text
                    _uiState.value = HomeUiState.Success(output)
                }

            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.localizedMessage ?: "Error in Generating content")
            }
        }
    }
}


sealed interface HomeUiState {
    object Initial : HomeUiState
    object Loading : HomeUiState
    data class Success(
        val outputText: String
    ) : HomeUiState

    data class Error(val error: String) : HomeUiState

}