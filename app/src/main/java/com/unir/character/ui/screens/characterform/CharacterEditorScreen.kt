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
    var form by remember { mutableStateOf(PersonalityTestForm()) }
    var isEditingPortrait by remember { mutableStateOf(false) }

    var characterToUpdate by remember(characterId) {
        mutableStateOf(
            if (characterId == 0L) {
                CharacterEntity()
            } else {
                editableCharacter?.copy() ?: CharacterEntity()
            }
        )
    }

    DefaultColumn {

        if (characterId == 0L) {
            DefaultRow{
                Text("Seleccionar Avatar")
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

        PersonalityTest( onValueChange = { form = it })

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
                    navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(characterToUpdate.id))
                }
            ) {
                Text("Guardar")
            }
        }
    }
}









// COPIA DE SEGURIDAD DEL FORMULARIO
@Composable
fun CharacterEditorFormCOPIASEGURIDAD(
    characterId: Long,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
) {
    LaunchedEffect(characterId) {
        characterViewModel.getCharacterById(characterId)
    }

    val editableCharacter by characterViewModel.selectedCharacter.collectAsState()
    var characterToUpdate by remember(editableCharacter) {
        mutableStateOf(editableCharacter?.copy())
    }

    characterToUpdate?.let { character ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(){
                TextField(
                    value = character.name,
                    onValueChange = { newName ->
                        characterToUpdate = character.copy(name = newName)
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier.weight(2.0f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                NumberRangeDropDown(
                    validRange = 1..12,
                    selectedValue = character.level,
                    modifier = Modifier.weight(1f),
                    label = "Nivel",
                    onValueChange = { newLevel ->
                        characterToUpdate = character.copy(_level = newLevel)
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
                    selectedOption = RolClass.getString(character.rolClass), // Mostrar la opción actual
                    onValueChange = { selectedOption ->
                        characterToUpdate = character.copy(rolClass = RolClass.getClass(selectedOption))
                    },
                    label = "Clase",
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(end = 16.dp)

                )

                DropDownText(
                    options = Race.getListOf(),
                    selectedOption = Race.getString(character.race),
                    label = "Raza",
                    onValueChange = { selectedOption ->
                        characterToUpdate = character.copy(race = Race.getRace(selectedOption))
                    },
                    modifier = Modifier.weight(1.0f)

                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = character.description,
                onValueChange = { desc ->
                    characterToUpdate = character.copy(description = desc)
                },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                singleLine = false
            )


            Spacer(modifier = Modifier.height(16.dp))

            StatSectionForm(
                character = characterToUpdate!!,
                onValueChange = { updatedCharacter -> characterToUpdate = updatedCharacter }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Button(
                    colors = ButtonDefaults.buttonColors( containerColor = MaterialTheme.colorScheme.primary),
                    onClick = {
                        characterToUpdate?.let { updatedCharacter ->
                            characterViewModel.saveCharacter(updatedCharacter)
                            navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(updatedCharacter.id))
                        }
                    }
                ) {
                    Text("Guardar")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    colors = ButtonDefaults.buttonColors( containerColor = MaterialTheme.colorScheme.error),
                    onClick = {
                        characterViewModel.deleteCharacter(character)
                    }
                ) {
                    Text("Eliminar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    colors = ButtonDefaults.buttonColors( containerColor = MaterialTheme.colorScheme.scrim),
                    onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(character.id)) }
                ) {
                    Text("Cancelar")
                }

            }

        }
    }
}

