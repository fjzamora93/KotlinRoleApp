package com.roleapp.adventure.ui.screens

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
<<<<<<< Updated upstream
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
=======
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
>>>>>>> Stashed changes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.R
import com.unir.roleapp.adventure.ui.viewmodels.CreateAdventureViewModel

@Composable
<<<<<<< Updated upstream
fun AdventureMainScreen() {
    LaunchedEffect(Unit) {
        Log.d("CharacterScreen", "NavController está listo")
    }
=======
fun AdventureMainScreen(
    navController: NavHostController,
    viewModel: CreateAdventureViewModel = hiltViewModel()
) {
    val title       by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val loading     by viewModel.loading.collectAsState()
    val error       by viewModel.error.collectAsState()
>>>>>>> Stashed changes

    MainLayout {
        Column(
            Modifier
                .fillMaxSize()
<<<<<<< Updated upstream
                .background(Brush.verticalGradient(colors = listOf(Color(0xFF1F1D36), Color(0xFF3F3351))))
=======
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
>>>>>>> Stashed changes
        ) {
            Spacer(Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.fantasy_landscape),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(24.dp))

            Text("Nueva aventura", style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = viewModel::onTitleChange,
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.createAdventure { adventure ->
                        // Paso 1: vamos a CreateAdventureScreen
                        navController.navigate(ScreensRoutes.CreateAdventureScreen.route)
                    }
                },
                enabled = !loading && title.isNotBlank() && description.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (loading) CircularProgressIndicator(Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                else Text("Crear Aventura")
            }
        }
    }
}
<<<<<<< Updated upstream

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

        Button(
            onClick = {navigationViewModel.navigate(ScreensRoutes.AiTestScreen.route) },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                "Test Generador IA",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
            )
        }


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

=======
>>>>>>> Stashed changes
