package com.unir.character.ui.screens.characterform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes
import com.ui.layout.MainLayout
import com.unir.character.data.model.local.Race
import com.unir.character.data.model.local.RolClass
import com.unir.character.ui.screens.common.DropDownText
import com.unir.character.ui.screens.common.NumberRangeDropDown
import com.unir.character.ui.screens.skills.SkillForm
import com.unir.character.viewmodels.CharacterViewModel


@Composable
fun CharacterEditorScreen(characterId: Long){
    MainLayout(){
        Column{
            CharacterEditForm(characterId)
        }
    }
}

/**TODO: PENDIENTE DE AÑADIR AL FORMULARIO
 * - Imagen
 * - Nivel
 * - Stats
 * - Skills
 * - Armadura
 * - Iniciativa
 * - Puntos de golpe.
 * - Puntos de magia
 *
 * */
@Composable
fun CharacterEditForm(
    characterId: Long,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
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
            modifier = Modifier.fillMaxWidth().padding(16.dp)
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
                    modifier = Modifier.weight(1.0f).padding(end = 16.dp)

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
                            characterViewModel.updateCharacter(updatedCharacter)
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

