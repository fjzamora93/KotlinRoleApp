package com.unir.roleapp.adventure.ai.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.adventure.ai.viewmodels.SceneViewModel

@Composable
fun SceneTestScreen(
    viewModel: SceneViewModel = hiltViewModel()
) {
    val imageBitmap by viewModel.imageBitmap.collectAsState()

    MainLayout {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF1F1D36), Color(0xFF3F3351))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        viewModel.generarImagen(
                            prompt = "<lora:dnd-maps:1>top-down view of a medieval tavern, pixel art style, stone floor, wooden bar and tables"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(
                        "Generar imagen IA",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Imagen generada",
                        modifier = Modifier
                            .size(256.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                    )
                } ?: Text(
                    text = "Pulsa el botón para generar una imagen",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.LightGray),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
