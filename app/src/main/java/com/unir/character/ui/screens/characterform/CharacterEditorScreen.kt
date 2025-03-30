package com.unir.character.ui.screens.characterform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.core.di.LocalNavigationViewModel
import com.unir.core.navigation.NavigationViewModel
import com.unir.core.navigation.ScreensRoutes
import com.unir.core.ui.components.common.DefaultColumn
import com.unir.core.ui.components.common.DefaultRow
import com.unir.core.ui.components.buttons.BackButton
import com.unir.core.ui.layout.MainLayout
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Race
import com.unir.character.data.model.local.RolClass
import com.unir.character.ui.screens.common.DropDownText
import com.unir.character.ui.screens.common.NumberRangeDropDown
import com.unir.character.ui.screens.characterform.components.PersonalityTest
import com.unir.character.ui.screens.characterform.components.PersonalityTestForm
import com.unir.character.ui.screens.characterform.components.PortraitGridComponent
import com.unir.character.ui.screens.characterform.components.StatSectionForm
import com.unir.character.viewmodels.CharacterViewModel


@Composable
fun CharacterEditorScreen(characterId: Long){
    MainLayout {
        CharacterEditForm(characterId)

    }
}


@Composable
fun CharacterEditForm(
    characterId: Long = 0,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
) {
    LaunchedEffect(characterId) {
        if (characterId != 0L) {
            characterViewModel.getCharacterById(characterId)
        }
    }

    val editableCharacter by characterViewModel.selectedCharacter.collectAsState()
    val loadingState by characterViewModel.loadingState.collectAsState()

    var form by remember { mutableStateOf(PersonalityTestForm()) }
    var isEditingPortrait by remember { mutableStateOf(false) }
    val saveState by characterViewModel.saveState.collectAsState()
    var characterToUpdate by remember { mutableStateOf(CharacterEntity()) }
    LaunchedEffect(editableCharacter) {
        if (editableCharacter != null) {
            characterToUpdate = editableCharacter!!.copy()
        }
    }

    // Tan pronto se haya guardado un personaje, navegar a la pantalla de detalles (tiene un retardo)
    LaunchedEffect(saveState) {
        saveState?.let { id ->
            navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(id))
        }
    }

    DefaultColumn {

        if (characterId == 0L) {
            DefaultRow{
                Text("Seleccionar Avatar", style = MaterialTheme.typography.titleMedium, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                IconButton(
                    onClick = { isEditingPortrait = true }
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Seleccionar Avatar"
                    )
                }

                if (isEditingPortrait) {
                    Dialog(onDismissRequest = { isEditingPortrait = false }) {
                        PortraitGridComponent(
                            onPortraitSelected = { portraitString ->
                                characterToUpdate = characterToUpdate.copy(imgUrl = portraitString)
                                isEditingPortrait = false
                            },
                            onBackPressed = { isEditingPortrait = false }
                        )

                    }
                }

            }
        }


        Row(){
            TextField(
                value = characterToUpdate.name,
                onValueChange = { newName ->
                    characterToUpdate = characterToUpdate.copy(name = newName)
                },
                label = { Text("Nombre") },
                modifier = Modifier.weight(2.0f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            NumberRangeDropDown(
                validRange = 1..12,
                selectedValue = characterToUpdate.level,
                modifier = Modifier.weight(1f),
                label = "Nivel",
                onValueChange = { newLevel ->
                    characterToUpdate = characterToUpdate.copy(_level = newLevel)
                }

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            DropDownText(
                options = RolClass.getListOf(),
                selectedOption = RolClass.getString(characterToUpdate.rolClass), // Mostrar la opción actual
                onValueChange = { selectedOption ->
                    characterToUpdate = characterToUpdate.copy(rolClass = RolClass.getClass(selectedOption))
                },
                label = "Clase",
                modifier = Modifier
                    .weight(1.0f)
                    .padding(end = 16.dp)

            )

            DropDownText(
                options = Race.getListOf(),
                selectedOption = Race.getString(characterToUpdate.race),
                label = "Raza",
                onValueChange = { selectedOption ->
                    characterToUpdate = characterToUpdate.copy(race = Race.getRace(selectedOption))
                },
                modifier = Modifier.weight(1.0f)

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = characterToUpdate.description,
            onValueChange = { desc ->
                characterToUpdate = characterToUpdate.copy(description = desc)
            },
            label = { Text("Trasfondo") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,
            singleLine = false
        )


        Spacer(modifier = Modifier.height(16.dp))

        StatSectionForm(
            character = characterToUpdate,
            onValueChange = { updatedCharacter -> characterToUpdate = updatedCharacter }
        )

        // Test de personalidad solo para nuevos personajes
        if (characterId == 0L) {
            PersonalityTest(onValueChange = { form = it })
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            BackButton()
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors( containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    characterViewModel.saveCharacter(characterToUpdate, form)
                }
            ) {
                Text("Guardar")
            }
        }
    }
}




