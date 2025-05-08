package com.unir.roleapp.adventure.ui.screens

import com.unir.roleapp.adventure.ui.viewmodels.CreateAdventureViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.unir.roleapp.core.navigation.ScreensRoutes


@Composable
fun CreateAdventureScreen(
    navController: NavController,
    viewModel: CreateAdventureViewModel = hiltViewModel(),
    onNext: (String) -> Unit
) {
    // Recoge los StateFlows del ViewModel
    val title       by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val loading     by viewModel.loading.collectAsState()
    val error       by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = viewModel::onTitleChange,
            label = { Text("Título de la partida") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = viewModel::onDescriptionChange,
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = {
            viewModel.createAdventure { adv ->
                navController.navigate(ScreensRoutes.WaitingRoomScreen.createRoute(adv.id))
            }
            },
            enabled = !loading && title.isNotBlank() && description.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}
