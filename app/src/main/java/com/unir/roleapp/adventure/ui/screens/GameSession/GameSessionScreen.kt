package com.unir.roleapp.adventure.ui.screens.GameSession


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.ui.screens.common.DropDownText

import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.adventure.data.model.GameSession
import com.unir.roleapp.adventure.ui.viewmodels.GameSessionViewModel
import com.unir.roleapp.character.data.model.local.ItemCategory
import com.unir.roleapp.character.ui.screens.common.RectangularButton
import com.unir.roleapp.character.ui.screens.items.components.InventoryByCategorySection
import com.unir.roleapp.character.ui.screens.items.components.ItemForm
import com.unir.roleapp.character.ui.screens.items.components.ItemSummaryComponent
@Composable
fun GameSessionScreen(
    gameSessionViewModel: GameSessionViewModel = hiltViewModel()
) {
    val gameSessions by gameSessionViewModel.gameSessions.collectAsState()
    val selectedSession by gameSessionViewModel.selectedSession.collectAsState()
    val loading by gameSessionViewModel.loading.collectAsState()
    val errorMessage by gameSessionViewModel.errorMessage.collectAsState()

    var currentView by remember { mutableStateOf("list") } // "list", "detail", "form"

    var newName by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }

    MainLayout {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1F1D36), Color(0xFF3F3351))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        gameSessionViewModel.fetchGameSessions()
                        currentView = "list"
                    }) {
                        Text("Ver sesiones")
                    }
                    Button(onClick = {
                        gameSessionViewModel.fetchGameSessionById("sessionIdDemo")
                        currentView = "detail"
                    }) {
                        Text("Buscar por ID")
                    }
                    Button(onClick = {
                        currentView = "form"
                    }) {
                        Text("Crear nueva")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (loading) {
                    CircularProgressIndicator()
                }

                errorMessage?.let {
                    Text("Error: $it", color = Color.Red)
                }

                when (currentView) {
                    "list" -> {
                        gameSessions.forEach { session ->
                            DefaultRow {
                                Text(session.id.toString(), color = Color.White)
                            }
                        }
                    }
                    "detail" -> {
                        selectedSession?.let { session ->
                            Text("ID: ${session.id}", color = Color.White)
                            Text("Nombre: ${session.id}", color = Color.White)
                            Text("Descripción: ${session.id}", color = Color.White)
                        } ?: Text("No hay sesión seleccionada", color = Color.Gray)
                    }
                    "form" -> {
                        Column {
                            OutlinedTextField(
                                value = newName,
                                onValueChange = { newName = it },
                                label = { Text("Nombre de sesión") }
                            )
                            OutlinedTextField(
                                value = newDescription,
                                onValueChange = { newDescription = it },
                                label = { Text("Descripción") }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                val newSession = GameSession(id = newName)
                                gameSessionViewModel.addGameSession(newSession)
                                newName = ""
                                newDescription = ""
                                currentView = "list"
                            }) {
                                Text("Guardar sesión")
                            }
                        }
                    }
                }
            }
        }
    }
}
