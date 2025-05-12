package com.unir.roleapp.adventure.ui.screens.form

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.adventure.ui.viewmodels.AdventureFormViewModel

@Composable
fun HistoricalContextScreen(
    onNext: () -> Unit,
    viewModel: AdventureFormViewModel,
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
                HistoricalContextScreenBody(
                    onNext,
                    viewModel
                )
            }
        }
    }
}

@Composable
fun HistoricalContextScreenBody(
    onNext: () -> Unit,
    viewModel: AdventureFormViewModel,
) {
    val textColor = Color.White;

    // ① Recogemos los flows como estados Compose
    val id by viewModel.id.collectAsState(initial = "")
    val historicalContext by viewModel.historicalContext.collectAsState(initial = "")
    val characters by viewModel.characters.collectAsState(initial = emptyList())
    val clipboard = LocalClipboardManager.current

    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Inicio de la aventura", style = MaterialTheme.typography.h5, color = textColor)
        Spacer(Modifier.height(15.dp))

        // ② Código de partida + copiado al portapapeles
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Código partida:", color = textColor)
            Spacer(Modifier.width(8.dp))
            Button(onClick = { clipboard.setText(AnnotatedString(id)) }) {
                Text(id.ifBlank { "—" })
            }
        }
        Spacer(Modifier.height(30.dp))

        // ③ Contexto histórico
        Text("Contexto histórico", style = MaterialTheme.typography.subtitle1, color = textColor,
            modifier = Modifier.padding(bottom = 10.dp))
        Text(
            "Escribe el contexto histórico en el cuál se desarrolla tu aventura mientras más detalles escribas mejor.",
            style = MaterialTheme.typography.body2, color = textColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = historicalContext,
            onValueChange = viewModel::onChangeHistoricalContext,
            label = { Text("Escribe el contexto histórico...", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                textColor = Color.White
            )
        )
        Spacer(Modifier.height(40.dp))

        // ④ Listado de jugadores ya añadidos
        Text("Listado jugadores", style = MaterialTheme.typography.subtitle1, color = textColor)
        Text(
            "Envía el código de partida a tus amigos para que se puedan unir a la aventura, o has click en el “+” y busca a uno de tus amigos.",
            style = MaterialTheme.typography.body2, color = textColor
        )
        Spacer(Modifier.height(8.dp))
        Row {
            characters.forEach { char ->
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(char.name.take(1))
                }
                Spacer(Modifier.width(8.dp))
            }
        }
        Spacer(Modifier.height(16.dp))

        // ⑤ Botón Continuar
        Button(
            onClick = onNext,
            enabled = characters.size >= 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}