package com.unir.roleapp.adventure.ui.screens

import com.unir.roleapp.adventure.ui.components.CharacterSelectionList
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.adventure.ui.viewmodels.AdventureContextViewModel

@Composable
fun AdventureContextScreen(
    adventureId: String,
    onCancel:    () -> Unit,
    onFinish:    () -> Unit,
    viewModel: AdventureContextViewModel = hiltViewModel()
) {
    // 1) Dispara la carga de título/descrición al montar
    LaunchedEffect(adventureId) {
        viewModel.loadAdventure(adventureId)
    }

    // 2) Lee los flujos del ViewModel
    val title       by viewModel.title.collectAsState()
    val description by viewModel.description.collectAsState()
    val histCtx     by viewModel.historicalContext.collectAsState()
    val selectedIds by viewModel.selectedCharacterIds.collectAsState()
    val charCtxMap  by viewModel.charContexts.collectAsState()
    val loading     by viewModel.loading.collectAsState()
    val error       by viewModel.error.collectAsState()
    val allChars    = viewModel.sampleChars

    MainLayout {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 1. Fila: título / ID
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment   = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)
                Text(
                    text  = adventureId,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(12.dp))

            // 2. Descripción (máx. 2 líneas)
            Text(
                text     = description,
                style    = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Spacer(Modifier.height(16.dp))

            // 3. Contexto histórico
            OutlinedTextField(
                value         = histCtx,
                onValueChange = viewModel::onHistoricalContextChange,
                label         = { Text("Contexto histórico") },
                modifier      = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape         = RoundedCornerShape(8.dp)
            )

            Spacer(Modifier.height(16.dp))

            // 4. Personajes
            Text("Personajes", style = MaterialTheme.typography.titleMedium)
            Text(
                "Selecciona hasta 4 personajes y escribe su contexto",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            // 5. Lista de personajes (checkbox)
            CharacterSelectionList(
                characters  = allChars,      // <- aquí la lista fija
                selectedIds = selectedIds,
                onSelect    = viewModel::selectChar,
                modifier    = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )


            Spacer(Modifier.height(16.dp))

            // 6. Contexto del personaje seleccionado
            val currentId = selectedIds.firstOrNull()
            val currentCtx = currentId?.let { charCtxMap[it] } ?: ""
            OutlinedTextField(
                value         = currentCtx,
                onValueChange = { viewModel.updateCharContext(currentId!!, it) },
                label         = { Text("Contexto para personaje") },
                modifier      = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape         = RoundedCornerShape(8.dp)
            )

            Spacer(Modifier.weight(1f))

            // 7. Botones Cancelar / Crear
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = {
                        viewModel.deleteAdventure(adventureId) {
                            onCancel()
                        }
                    }
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        viewModel.saveAll(adventureId) { onFinish() }
                    },
                    enabled = !loading && histCtx.isNotBlank() && selectedIds.isNotEmpty()
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            Modifier.size(20.dp),
                            color       = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Crear Aventura")
                    }
                }
            }

            // 8. Mostrar error si hay
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
}
