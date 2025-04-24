package com.roleapp.character.ui.screens.characterform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.common.DefaultColumn
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.Race
import com.roleapp.character.data.model.local.RolClass
import com.roleapp.character.ui.screens.characterSheet.components.CharacterPortrait
import com.roleapp.character.ui.screens.common.DropDownText
import com.roleapp.character.ui.screens.common.NumberRangeDropDown
import com.roleapp.character.ui.screens.characterform.components.PersonalityTest
import com.roleapp.character.ui.screens.characterform.components.PersonalityTestForm
import com.roleapp.character.ui.screens.characterform.components.PortraitGridComponent
import com.roleapp.character.ui.screens.characterform.components.StatSectionForm
import com.roleapp.character.ui.screens.common.layout.CharacterHeader
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.unir.roleapp.R


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

    var isRequired by remember { mutableStateOf(characterToUpdate.name.isBlank()) }
    var isValidStat by remember { mutableStateOf(true) }

    LaunchedEffect(editableCharacter) {
        if (editableCharacter != null) {
            characterToUpdate = editableCharacter!!.copy()
        }
        isRequired = characterToUpdate.name.isBlank()
    }

    // Tan pronto se haya guardado un personaje, navegar a la pantalla de detalles (tiene un retardo)
    LaunchedEffect(saveState) {
        saveState?.let { id ->
            navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(id))
        }
    }

    LaunchedEffect(editableCharacter) {
        if (editableCharacter != null) {
            characterToUpdate = editableCharacter!!.copy()
        }
    }

    DefaultColumn {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { isEditingPortrait = true }
        ){

            if (characterToUpdate.imgUrl.isEmpty()){
                IconButton ( onClick = {  isEditingPortrait = true  }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        painter = painterResource(id = R.drawable.baseline_portrait_24),
                        contentDescription = "Seleccionar Avatar",
                        modifier = Modifier.size(240.dp)
                    )
                }

                Text(
                    "Seleccionar Avatar",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = colorResource(id = R.color.white)

                )
            } else {
                CharacterPortrait(
                    size = 60,
                    character = characterToUpdate,
                    modifier = Modifier.size(100.dp).clip(CircleShape)
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

        HorizontalDivider(modifier = Modifier.padding(16.dp))

        Row(){

            TextField(
                value = characterToUpdate.name,
                onValueChange = { newName ->
                    characterToUpdate = characterToUpdate.copy(name = newName)
                    isRequired = newName.isBlank() // Valida al escribir
                },
                label = { Text("Nombre", color = colorResource(id = R.color.gray)) },
                modifier = Modifier.weight(2.0f),
                isError = isRequired, // Activa el estado de error
                supportingText = {
                    if (isRequired) {
                        Text("Este campo es obligatorio", color = Color.Red)
                    }
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            NumberRangeDropDown(
                validRange = 1..12,
                selectedValue = characterToUpdate._level,
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


        Spacer(modifier = Modifier.height(24.dp))


        // Test de personalidad solo para nuevos personajes
        if (characterId == 0L) {
            PersonalityTest(onValueChange = { form = it })
        } else {
            StatSectionForm(
                character = characterToUpdate,
                onValueChange = { updatedCharacter -> characterToUpdate = updatedCharacter },
                isValid = { isValidStat = it  }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = isRequired.not() && isValidStat,
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




