package com.unir.sheet.ui.screens.character.characterDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.ui.navigation.NavigationViewModel
import com.unir.sheet.ui.screens.character.haracterDetail.StatSection
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel

@Composable
fun CharacterDetailScreen(
    characterId : Int,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
){

    characterViewModel.getCharacterById(characterId)

    MainLayout(){
        Column(Modifier.fillMaxSize().padding(16.dp)
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
    val selectedCharacter by characterViewModel.selectedCharacter.observeAsState()

    // Si el personaje no está seleccionado, mostramos un texto vacío o de espera
    if (selectedCharacter == null) {
        Text("Cargando personaje...")
    } else {
        // Mantén un solo estado compartido
        var editableCharacter by remember { mutableStateOf(selectedCharacter!!) }

        CharacterMenu()


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(modifier = Modifier.weight(1.5f)){
                CharacterPortrait(
                    character = editableCharacter,
                    context = LocalContext.current
                )
            }

            Column(modifier = Modifier.weight(1f)){
                InfoSection(
                    editableCharacter = editableCharacter,
                    onCharacterChange = {
                        editableCharacter = it
                        characterViewModel.updateCharacter(it)
                    }
                )
            }

        }



        // CAMPOS NUMÉRICOS Y STATS
        StatSection(
            editableCharacter = editableCharacter,
            onCharacterChange = {
                editableCharacter = it
                characterViewModel.updateCharacter(it)
            }
        )

    }
}
