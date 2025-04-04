package com.roleapp.adventure.ui.screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.adventure.data.model.Scene
import com.roleapp.adventure.ui.viewmodels.SceneViewModel
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

    var newScenario by remember() { mutableStateOf(Scene("Título", "Descripción")) }

    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.fantasy_landscape),
            contentDescription = "Paisaje de fantasía",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(0.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(32.dp))

        androidx.compose.material.Text(
            text = "Nueva aventura",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(

            value = newScenario.name,
            onValueChange = { newName ->
                newScenario = newScenario.copy(name = newName)
            },
            shape = RoundedCornerShape(4.dp),
            label = { Text("Título") },
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier.height(200.dp),
            value = newScenario.description,
            onValueChange = { newDesc ->
                newScenario = newScenario.copy(description = newDesc)
            },
            shape = RoundedCornerShape(4.dp),
            label = { Text("Descripción") },
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { sceneViewModel.addScene(newScenario)  },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text("Añadir escenario a FireBase", style= MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }


        // Botón para navegar al último escenario creado
        Button(
            onClick = { navigationViewModel.navigate(ScreensRoutes.TemplateAdventureScreen.route)  },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text("Ver escenarios", style= MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }

        // Decoración: Imagen con temática de rol
        /*Image(
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
        )*/
    }
}

