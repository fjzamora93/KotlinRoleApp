package com.unir.character.ui.screens.characterSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unir.character.data.model.local.RolClass

import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.unir.character.ui.screens.character.CharacterCreatorForm
import com.unir.character.ui.screens.character.haracterDetail.StatSection
import com.ui.components.BackButton
import com.ui.components.TextBodyMedium
import com.ui.layout.MainLayout
import com.unir.character.viewmodels.CharacterViewModel

@Composable
fun CharacterDetailScreen(
    characterId : Long,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
){

    characterViewModel.getCharacterById(characterId)

    MainLayout(){
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            DetailCharacterBody()
            BackButton()
        }
    }
}


@Composable
fun DetailCharacterBody(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    navigation: NavigationViewModel = LocalNavigationViewModel.current,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    var isEditing by remember { mutableStateOf(false) }

    // Si el personaje no está seleccionado, mostramos un texto vacío o de espera
    if (selectedCharacter == null) {
        Text("Cargando personaje...")
    } else {

        // Puesto que el estado de null puede cambiar, llamamos al let
        selectedCharacter?.let { character ->
            CharacterMenu()
            IconButton(onClick = { isEditing = !isEditing }) {
                Icon(imageVector = Icons.Default.EditOff, contentDescription = "Add")
            }

            if (!isEditing) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1.5f)) {
                        CharacterPortrait(character)
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        TextBodyMedium(text = "Name: ${character.name} ",)
                        TextBodyMedium(text = "Descripción: ${character.description} ",)
                        TextBodyMedium(text = RolClass.getString(character.rolClass),)
                        TextBodyMedium(text = character.race.toString(),)
                    }
                }
            } else {
                CharacterCreatorForm(
                    isEditing = true,
                    onEditComplete = {
                        isEditing = it
                    }
                )
            }

            // CAMPOS NUMÉRICOS Y STATS
            StatSection(
                editableCharacter = character,
                onCharacterChange = {
                    characterViewModel.updateCharacter(it)
                }
            )
        }
    }
}
