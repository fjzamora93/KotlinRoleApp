package com.unir.roleapp.adventure.ui.screens.GameSession


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.adventure.data.model.GameSession
import com.unir.roleapp.adventure.ui.viewmodels.GameSessionViewModel

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
                        gameSessionViewModel.observeCurrentGameSession("sessionIdDemo")
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
