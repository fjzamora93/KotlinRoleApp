package com.unir.roleapp.adventure.ui.screens.form

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.roleapp.adventure.domain.model.AdventureAct
import com.unir.roleapp.adventure.ui.viewmodels.AdventureFormViewModel

@Composable
fun ActsScreen(
    viewModel: AdventureFormViewModel = hiltViewModel()
) {
    // Collect the list of acts from the ViewModel
    val acts by viewModel.acts.collectAsState(initial = emptyList())
    var selectedTab by remember { mutableStateOf(0) }

    // Temporary fields for a new act before it's added
    var newTitle by remember { mutableStateOf("") }
    var newNarrative by remember { mutableStateOf("") }
    var newMapDesc by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        Text("Creación de los actos", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(12.dp))

        // Tabs for existing acts + a '+' tab to add a new one
        ScrollableTabRow(selectedTabIndex = selectedTab) {
            acts.forEachIndexed { index, act ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text("Acto ${act.actNumber}") }
                )
            }
            Tab(
                selected = false,
                onClick = {
                    // Reset temp fields and add a blank act
                    newTitle = ""
                    newNarrative = ""
                    newMapDesc = ""
                    viewModel.addAct(
                        AdventureAct(
                            actNumber = 0,
                            title = newTitle,
                            narrative = newNarrative,
                            mapDescription = newMapDesc,
                            // pass any additional lists as needed
                            emptyList()
                        )
                    )
                    selectedTab = acts.size // switch to the new tab
                },
                text = { Icon(Icons.Default.Add, contentDescription = "Nuevo acto") }
            )
        }
        Spacer(Modifier.height(16.dp))

        // Determine if we are editing an existing act or the new one
        val currentAct = acts.getOrNull(selectedTab)
        val titleValue = currentAct?.title ?: newTitle
        val narrativeValue = currentAct?.narrative ?: newNarrative
        val mapDescValue = currentAct?.mapDescription ?: newMapDesc

        // Title field
        OutlinedTextField(
            value = titleValue,
            onValueChange = { text ->
                if (currentAct != null) {
                    viewModel.onChangeAct(currentAct.copy(title = text))
                } else {
                    newTitle = text
                }
            },
            label = { Text("Título") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // Narrative field
        OutlinedTextField(
            value = narrativeValue,
            onValueChange = { text ->
                if (currentAct != null) {
                    viewModel.onChangeAct(currentAct.copy(narrative = text))
                } else {
                    newNarrative = text
                }
            },
            label = { Text("Contexto narrativo") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(Modifier.height(8.dp))

        // Map description field
        OutlinedTextField(
            value = mapDescValue,
            onValueChange = { text ->
                if (currentAct != null) {
                    viewModel.onChangeAct(currentAct.copy(mapDescription = text))
                } else {
                    newMapDesc = text
                }
            },
            label = { Text("Descripción del entorno") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = {
                viewModel.submitAdventure(
                    onSuccess = { /* TODO */ },
                    onError = { /* TODO */ }
                )
            },
            enabled = acts.isNotEmpty() && acts.all {
                it.title.isNotBlank() &&
                        it.narrative.isNotBlank() &&
                        it.mapDescription.isNotBlank()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Aventura")
        }
    }
}
