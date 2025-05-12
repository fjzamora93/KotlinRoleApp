package com.unir.roleapp.adventure.ui.screens.form

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.roleapp.adventure.ui.viewmodels.AdventureFormViewModel

@Composable
fun TitleScreen(
    onNext: () -> Unit,
    viewModel: AdventureFormViewModel = hiltViewModel()
) {
    // ① Recogemos los StateFlow como estados Compose
    val id by viewModel.id.collectAsState(initial = "")
    val title by viewModel.title.collectAsState(initial = "")
    val description by viewModel.description.collectAsState(initial = "")
    val error by viewModel.error.collectAsState(initial = null)

    Column(Modifier.padding(16.dp)) {
        Text("Datos básicos", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(12.dp))

        // ② Título
        OutlinedTextField(
            value = title,
            onValueChange = viewModel::onChangeTitle,
            label = { Text("Título de la aventura") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        // ③ Descripción
        OutlinedTextField(
            value = description,
            onValueChange = viewModel::onChangeDescription,
            label = { Text("Descripción breve") },
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(Modifier.height(16.dp))

        // ④ Mostrar error si existe
        error?.let { errMsg ->
            Text(
                text = errMsg,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // ⑤ Botón Siguiente
        Button(
            onClick = {
                // Llamamos al caso de uso createAdventure
                viewModel.createAdventure { adv ->
                    Log.d("AdventureMain", "ID generado: ${adv.id}")
                    // Guardamos el id en el StateFlow
                    viewModel.onChangeId(adv.id)
                    onNext()
                }
            },
            enabled = title.isNotBlank() && description.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}