package com.unir.roleapp.adventure.ui.screens.form

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.R
import com.unir.roleapp.adventure.ui.viewmodels.AdventureFormViewModel

@Composable
fun TitleScreen(
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
                TitleScreenBody(
                    onNext,
                    viewModel
                )
            }
        }
    }
}

@Composable
fun TitleScreenBody(
    onNext: () -> Unit,
    viewModel: AdventureFormViewModel = hiltViewModel()
) {
    val textColor = Color.White;

    val title by viewModel.title.collectAsState(initial = "")
    val description by viewModel.description.collectAsState(initial = "")
    val error by viewModel.error.collectAsState(initial = null)

    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.fantasy_landscape),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, androidx.compose.material3.MaterialTheme.colorScheme.onSurface, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(30.dp))

        Text("Nueva aventura",
            style = MaterialTheme.typography.h5,
            color = textColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text("Para comenzar una nueva aventura crea un título y una breve descripción que " +
                "permita a tus jugadores entender de qué irá la aventura que están por comenzar " +
                "a jugar.",
            style = MaterialTheme.typography.body1, color = textColor,
            modifier = Modifier.padding(bottom = 20.dp)
        )


        OutlinedTextField(
            value = title,
            onValueChange = viewModel::onChangeTitle,
            label = { Text("Título de la aventura", color = Color.Gray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                textColor = Color.White
            )

        )
        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = description,
            onValueChange = viewModel::onChangeDescription,
            label = { Text("Descripción breve", color = Color.Gray) },
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                textColor = Color.White
            )
        )
        Spacer(Modifier.height(50.dp))

        error?.let { errMsg ->
            Text(
                text = errMsg,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                viewModel.createAdventure { adv ->
                    Log.d("AdventureMain", "ID generado: ${adv.id}")
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