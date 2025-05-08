package com.unir.roleapp.adventure.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.roleapp.adventure.ui.viewmodels.AdventureContextViewModel
import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.character.ui.viewmodels.CharacterViewModel

@Composable
fun AdventureContextScreen(
    adventureId: String,
    viewModel: AdventureContextViewModel = hiltViewModel(),
    onCancel: () -> Unit,
    onFinish: () -> Unit
) {
    // Estados del VM
    val histCtx        by viewModel.historicalContext.collectAsState(initial = "")
    val selectedIds    by viewModel.selectedCharacterIds.collectAsState()
    val charCtxMap     by viewModel.charContexts.collectAsState()
    val loading        by viewModel.loading.collectAsState()
    val error          by viewModel.error.collectAsState()

    // Lista de personajes desde CharacterViewModel
    val characterViewModel: CharacterViewModel = hiltViewModel()
    val allChars by characterViewModel.characters.collectAsState(initial = emptyList())

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Contexto histórico y descripción de personajes",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = histCtx,
            onValueChange = { viewModel.updateHistoricalContext(it) },
            label = { Text("Contexto histórico") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Text("Selecciona hasta 4 personajes", style = MaterialTheme.typography.bodyLarge)

        // Listado de selección, usando CharacterEntity
        CharacterSelectionList(
            characters  = allChars,
            selectedIds = selectedIds,
            onSelect    = viewModel::selectChar,
            modifier    = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        // Campo de texto por cada personaje seleccionado
        LazyColumn {
            items(selectedIds) { charId ->
                Spacer(Modifier.height(8.dp))
                Text(
                    "Contexto para personaje $charId",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = charCtxMap[charId] ?: "",
                    onValueChange = { viewModel.updateCharContext(charId, it) },
                    placeholder = { Text("¿Quién es? ¿Traumas? ¿Inicio?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }
        }

        Spacer(Modifier.weight(1f))

        // Mostrar error si lo hay
        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }
            Button(
                onClick = { viewModel.saveAll { onFinish() } },
                enabled = histCtx.isNotBlank() && selectedIds.isNotEmpty() && !loading
            ) {
                Text("Crear partida")
            }
        }
    }
}

@Composable
fun CharacterSelectionList(
    characters: List<CharacterEntity>,
    selectedIds: List<Long>,
    onSelect: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(characters) { char ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedIds.contains(char.id),
                    onCheckedChange = { onSelect(char.id) }
                )
                Text(char.name, Modifier.padding(start = 8.dp))
            }
        }
    }
}
