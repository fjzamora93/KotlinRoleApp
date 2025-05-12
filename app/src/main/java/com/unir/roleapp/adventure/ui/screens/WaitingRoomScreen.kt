package com.unir.roleapp.adventure.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.adventure.domain.model.Character
import com.unir.roleapp.adventure.ui.screens.components.PlayerGrid

@Composable
fun WaitingRoomScreen(
  adventureId: String,
  characters: List<Character>,
  loading: Boolean? = false,
  error: String? = null,
  onCopyCode: () -> Unit,
  onAddPlayer: () -> Unit,
  onContinue: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainLayout(){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
            ){
                WaitingRoomScreenBody(
                    adventureId,
                    characters,
                    loading,
                    error,
                    onCopyCode,
                    onAddPlayer,
                    onContinue
                )
            }
        }
    }
}

@Composable
fun WaitingRoomScreenBody(
    adventureId: String,
    characters: List<Character>,
    loading: Boolean? = false,
    error: String? = null,
    onCopyCode: () -> Unit,
    onAddPlayer: () -> Unit,
    onContinue: () -> Unit
) {

    var textColor = Color.White;
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Sala de espera",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp),
            color = textColor
        )

        // Código de partida con botón copiable
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Código partida:",
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = onCopyCode) {
                Text(adventureId)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Subtítulo
        Text(
            text = "Listado jugadores",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp),
            color = textColor
        )

        // Descripción
        Text(
            text = "Envía el código de partida a tus amigos para que se puedan unir a la " +
                    "aventura,o haz click en el “+” y busca a uno de tus amigos.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = textColor
        )

        Spacer(Modifier.height(44.dp))

        // Cuadrícula de jugadores

        Spacer(Modifier.height(32.dp))

        // Botón Continuar
        Button(
            onClick = onContinue,
            enabled = characters.size >= 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}
