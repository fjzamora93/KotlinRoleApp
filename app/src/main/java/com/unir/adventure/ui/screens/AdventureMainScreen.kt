package com.unir.adventure.ui.screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.core.di.LocalNavigationViewModel
import com.unir.core.navigation.NavigationViewModel
import com.unir.core.navigation.ScreensRoutes
import com.unir.core.ui.layout.MainLayout
import com.unir.adventure.data.model.Scene
import com.unir.adventure.ui.viewmodels.SceneViewModel
import com.unir.roleapp.R

// TODO: NO HAY POR QUÉ USAR EL MAIN LAYOUT (que lo que tiene dentro es un Scaffold). Como alternativa, podemos crear un nuevo Scaffold.
@Composable
fun AdventureMainScreen() {
    LaunchedEffect(Unit) {
        Log.d("CharacterScreen", "NavController está listo")
    }

    MainLayout {
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(Color(0xFF1F1D36), Color(0xFF3F3351))))
        ) {
            AdventureScreenBody(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun AdventureScreenBody(
    modifier: Modifier = Modifier,
    sceneViewModel: SceneViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current
    ) {


    var newScenario by remember() { mutableStateOf(Scene("Nombre", "Descripción")) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo o imagen decorativa
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.d20_dice),
            contentDescription = "Logo de RolApp",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título principal
        Text(
            text = "RolApp",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF9F9F9)
            )
        )
        Text(
            text = "La app para gestionar juegos de rol",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFD4D4D8)),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))


        TextField(

            value = newScenario.name,
            onValueChange = { newName ->
                newScenario = newScenario.copy(name = newName)
            },
            label = { Text("Nombre") },

        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = newScenario.description,
            onValueChange = { newDesc ->
                newScenario = newScenario.copy(description = newDesc)
            },
            label = { Text("Descripción") },
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { sceneViewModel.addScene(newScenario)  },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text("Añadir escenario a FireBase", style= MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }


        // Botón para navegar al último escenario creado
        Button(
            onClick = { navigationViewModel.navigate(ScreensRoutes.TemplateAdventureScreen.route)  },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text("Ver escenarios", style= MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Decoración: Imagen con temática de rol
        Image(
            painter = painterResource(id = R.drawable.fantasy_landscape),
            contentDescription = "Paisaje de fantasía",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto adicional decorativo
        Text(
            text = "Explora, combate y haz crecer a tus personajes.",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFF4F4F5)),
            textAlign = TextAlign.Center
        )



        Spacer(modifier = Modifier.height(16.dp))
    }
}

