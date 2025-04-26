package com.unir.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.common.DefaultRow


@Composable
fun AdventureSearchDialog(
    onDismiss: () -> Unit,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    navigation : NavigationViewModel = LocalNavigationViewModel.current
) {
    val characterList by characterViewModel.characters.collectAsState()
    val defaultCharacter by characterViewModel.selectedCharacter.collectAsState()

    var selectedCharacter by remember {
        mutableStateOf(defaultCharacter ?: characterList.firstOrNull())
    }
    var adventureId by remember { mutableStateOf("") }
    val errorMessage by characterViewModel.errorMessage.collectAsState()

    val adventureIsReady by characterViewModel.gameSession.collectAsState()

    LaunchedEffect(adventureIsReady) {
        if (adventureIsReady) {
            navigation.navigate(ScreensRoutes.AdventureListScreen.route)
        }
    }


    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(1f) // 👈 Hacemos el diálogo más ancho
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Dropdown de selección de personaje
                CharacterDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    options = characterList,
                    selectedOption = selectedCharacter,
                    onValueChange = { selectedCharacter = it },
                    label = "Selecciona un personaje"
                )

                // Texto para la ID de aventura
                Text(
                    text = "Introduce la Id de la aventura",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // TextField para introducir la ID
                TextField(
                    value = adventureId,
                    onValueChange = { adventureId = it },
                    placeholder = { Text("ID de la aventura") },
                    singleLine = true,
                    isError = !errorMessage.isNullOrBlank(),
                    modifier = Modifier.fillMaxWidth()
                )
                if (!errorMessage.isNullOrBlank()) {
                    Text(
                        text = errorMessage.toString(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                // Botones
                DefaultRow {

                    Button(
                        onClick = { onDismiss() },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Cancelar",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            selectedCharacter?.let {
                                characterViewModel.addCharacterToAdventure(it, adventureId)

                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        enabled = adventureId.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            "Buscar",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                }


            }
        }
    }
}
