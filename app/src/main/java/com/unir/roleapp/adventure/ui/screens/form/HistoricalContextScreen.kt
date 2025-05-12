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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.R
import com.unir.roleapp.adventure.ui.screens.components.PlayerGrid
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

    val id by viewModel.id.collectAsState(initial = "")
    val historicalContext by viewModel.historicalContext.collectAsState(initial = "")
    val characters by viewModel.characters.collectAsState(initial = emptyList())
    val clipboard = LocalClipboardManager.current

    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Inicio de la aventura",
            style = MaterialTheme.typography.h5,
            color = textColor
        )
        Spacer(Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Código partida:", color = textColor)
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                clipboard.setText(AnnotatedString(id))
            }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (id.isNotBlank()) id else "—",
                        color = Color.White
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.content_copy_24dp),
                        contentDescription = "Copiar",
                        tint = Color.White
                    )
                }
            }
        }
        Spacer(Modifier.height(30.dp))

        Text(
            "Contexto histórico",
            style = MaterialTheme.typography.subtitle1,
            color = textColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )
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
                focusedBorderColor = textColor,
                unfocusedBorderColor = Color.Gray,
                textColor = textColor
            )
        )
        Spacer(Modifier.height(40.dp))

        Text(
            "Listado jugadores",
            style = MaterialTheme.typography.subtitle1,
            color = textColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            "Envía el código de partida a tus amigos para que se puedan unir a la aventura.",
            style = MaterialTheme.typography.body2,
            color = textColor
        )
        Spacer(Modifier.height(30.dp))
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
            PlayerGrid(players = characters)
        }
        Spacer(Modifier.height(40.dp))

        Button(
            onClick = onNext,
            enabled = historicalContext.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}