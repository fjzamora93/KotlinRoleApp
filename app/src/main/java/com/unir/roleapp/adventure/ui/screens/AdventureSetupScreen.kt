package com.unir.roleapp.adventure.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.unir.roleapp.adventure.domain.model.AdventureAct
import com.unir.roleapp.adventure.ui.viewmodels.AdventureSetupViewModel

@Composable
fun AdventureSetupScreen(
    viewModel: AdventureSetupViewModel,
    onNext: () -> Unit
) {
    val title       by viewModel.title.collectAsState()
    val desc        by viewModel.description.collectAsState()
    val context     by viewModel.context.collectAsState()
    val code        by viewModel.code.collectAsState()
    val chars       by viewModel.selectedCharacters.collectAsState()
    val acts        by viewModel.acts.collectAsState()
    val loading     by viewModel.loading.collectAsState()
    val error       by viewModel.error.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = title, onValueChange = { viewModel.title.value = it },
            label = { Text("Título de la partida") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = desc, onValueChange = { viewModel.description.value = it },
            label = { Text("Descripción breve") },
            modifier = Modifier.fillMaxWidth().height(100.dp)
        )
        OutlinedTextField(
            value = context, onValueChange = { viewModel.context.value = it },
            label = { Text("Contexto histórico y personajes") },
            modifier = Modifier.fillMaxWidth().height(140.dp)
        )
        OutlinedTextField(
            value = code, onValueChange = { viewModel.code.value = it },
            label = { Text("Código de partida") },
            modifier = Modifier.fillMaxWidth()
        )
        // Aquí un Row o LazyRow con retratos y checkboxes para chars
        Text("Personajes añadidos: ${chars.size}")
        Button(onClick = viewModel::generateScript, enabled = !loading) {
            Text("Generar guion (LLM)")
        }
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        acts.forEach { act -> ActCard(act) }
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = onNext /*volver*/) { Text("Cancelar") }
            Button(onClick = { viewModel.saveAdventure("ID_GENERADA") }, enabled = acts.isNotEmpty() && !loading) {
                Text("Continuar")
            }
        }
    }
}

@Composable
fun ActCard(act: AdventureAct) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text("Acto ${act.actNumber}", style = MaterialTheme.typography.titleMedium)
            act.scenes.forEach { scene ->
                Text("• ${scene.name}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}