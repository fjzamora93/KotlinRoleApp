package com.unir.roleapp.adventure.ai.viewmodels

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.roleapp.adventure.ai.service.StableDiffusionApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SceneViewModel : ViewModel() {

    private val apiClient = StableDiffusionApiClient()

    private val _imageBitmap = MutableStateFlow<ImageBitmap?>(null)
    val imageBitmap: StateFlow<ImageBitmap?> = _imageBitmap

    fun generarImagen(prompt: String) {
        android.util.Log.d("SceneViewModel", "Ejecutando generarImagen con prompt: $prompt")

        viewModelScope.launch {
            try {
                val response = apiClient.generateImage(prompt)
                android.util.Log.d("SceneViewModel", "Respuesta recibida: ${response.images.size} imagen(es)")

                val base64Image = response.images.firstOrNull()
                if (base64Image != null) {
                    android.util.Log.d("SceneViewModel", "Decodificando imagen base64")
                    _imageBitmap.value = decodeBase64ToImageBitmap(base64Image)
                } else {
                    android.util.Log.w("SceneViewModel", "La respuesta no contiene imágenes")
                }
            } catch (e: Exception) {
                android.util.Log.e("SceneViewModel", "Error al generar la imagen", e)
            }
        }
    }


    private fun decodeBase64ToImageBitmap(base64Str: String): ImageBitmap? {
        return try {
            val base64Clean = base64Str.removePrefix("data:image/png;base64,")
            val decodedBytes = Base64.decode(base64Clean, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
