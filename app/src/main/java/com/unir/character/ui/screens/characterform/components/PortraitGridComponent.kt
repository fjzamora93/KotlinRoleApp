package com.unir.character.ui.screens.characterform.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ui.components.DefaultRow
import com.unir.sheet.R


@Composable
fun PortraitGridComponent(
    onPortraitSelected: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

    // Lista de imágenes que comienzan con "portrait"
    val images = listOf(
        "portrait_human_bard_male",
        "portrait_human_bard_female",
        "portrait_human_cleric_female",
        "portrait_human_cleric_male",
        "portrait_human_rogue_female",
        "portrait_human_rogue_male",
        "portrait_human_explorer_female",
        "portrait_human_explorer_male",
        "portrait_human_wizard_female",
        "portrait_human_wizard_male",
        "portrait_human_warrior_female",
        "portrait_human_warrior_male",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fondo blanco
            .padding(top = 50.dp, bottom = 50.dp) // Padding arriba y abajo
    ) {
        // Botón de "volver atrás"
        DefaultRow{
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier.padding(16.dp) // Padding para el botón
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "SELECCIÓN DE RETRATO",
                style = MaterialTheme.typography.titleMedium,
            )
        }


        // Cuadrícula de imágenes
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // 3 columnas
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.weight(1f) // Esto asegura que el grid ocupe todo el espacio disponible
        ) {
            items(images.size) { index ->
                val imageName = images[index] // Nombre de la imagen

                // Cada imagen se muestra en la cuadrícula, y al hacer clic pasamos el nombre de la imagen
                Image(
                    painter = painterResource(id = context.resources.getIdentifier(imageName, "drawable", context.packageName)),
                    contentDescription = imageName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            onPortraitSelected(imageName) // Llamamos al callback con el nombre de la imagen
                        }
                )
            }
        }
    }
}


