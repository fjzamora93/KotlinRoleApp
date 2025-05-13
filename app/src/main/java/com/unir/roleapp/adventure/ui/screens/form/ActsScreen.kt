package com.unir.roleapp.adventure.ui.screens.form

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.adventure.domain.model.AdventureAct
import com.unir.roleapp.adventure.ui.viewmodels.AdventureFormViewModel

@Composable
fun ActsScreen(
    navController: NavHostController,
    viewModel: AdventureFormViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainLayout() {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
            ) {
                ActsScreenBody(
                    navController = navController,
                    viewModel
                )
            }
        }
    }
}

@Composable
fun ActsScreenBody(
    navController: NavHostController,
    viewModel: AdventureFormViewModel

) {
    val textColor = Color.White;
    val acts by viewModel.acts.collectAsState(initial = emptyList())
    var selectedTab by remember { mutableStateOf(0) }

    var newTitle by remember { mutableStateOf("") }
    var newNarrative by remember { mutableStateOf("") }
    var newMapDesc by remember { mutableStateOf("") }

    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Creación de los actos",
            style = MaterialTheme.typography.h5,
            color = textColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            "Crea los diferentes arcos y descripción de entornos que tendrá tu aventura.",
            style = MaterialTheme.typography.body2, color = textColor
        )
        Spacer(Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier
                .fillMaxWidth(),
            containerColor = Color.Transparent,
            contentColor = textColor,
            edgePadding = 0.dp
        ) {
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
                    newTitle = ""
                    newNarrative = ""
                    newMapDesc = ""
                    viewModel.addAct(
                        AdventureAct(
                            actNumber = 0,
                            title = newTitle,
                            narrative = newNarrative,
                            mapDescription = newMapDesc,
                            emptyList()
                        )
                    )
                    selectedTab = acts.size
                },
                text = { Icon(Icons.Default.Add, contentDescription = "Nuevo acto") }
            )
        }
        Spacer(Modifier.height(16.dp))


        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.8f),
            elevation = 4.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(Modifier.padding(16.dp)) {
                val currentAct = acts.getOrNull(selectedTab)
                val titleValue = currentAct?.title ?: newTitle
                val narrativeValue = currentAct?.narrative ?: newNarrative
                val mapDescValue = currentAct?.mapDescription ?: newMapDesc

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
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                )
                Spacer(Modifier.height(25.dp))

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
                        .height(160.dp)
                )
                Spacer(Modifier.height(25.dp))

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
                        .height(160.dp)
                )
                Spacer(Modifier.height(15.dp))
            }
        }

        Spacer(Modifier.height(35.dp))

        Button(
            onClick = {
                viewModel.saveWholeAdventure(
                    onSuccess = {
                        // Si todo OK vamos a AdventureHomeScreen
                        navController.navigate(ScreensRoutes.MyAdventuresScreen.route)
                    },
                    onError = { exception ->
                        // Log de Error
                        Log.e("AdventureForm", "Error al guardar la aventura", exception)
                    }
                )
            },
            enabled = acts.isNotEmpty() && acts.all { act ->
                act.title.isNotBlank() &&
                        act.narrative.isNotBlank() &&
                        act.mapDescription.isNotBlank()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Aventura")
        }

    }
}
