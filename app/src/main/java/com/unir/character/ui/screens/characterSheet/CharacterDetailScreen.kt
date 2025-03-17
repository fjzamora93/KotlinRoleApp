package com.unir.character.ui.screens.characterSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.unir.character.ui.screens.character.haracterDetail.StatSection
import com.ui.layout.MainLayout
import com.ui.theme.MedievalColours
import com.unir.character.ui.screens.character.haracterDetail.HitPointsBar
import com.unir.character.ui.screens.skills.SkillSection
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
        }
    }
}


@Composable
fun HeaderCharacterDetail(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()


}






@Composable
fun DetailCharacterBody(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    navigation: NavigationViewModel = LocalNavigationViewModel.current,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()

    // Si el personaje no está seleccionado, mostramos un texto vacío o de espera
    if (selectedCharacter == null) {
        Text("Cargando personaje...")
    } else {

        // Puesto que el estado de null puede cambiar, llamamos al let
        selectedCharacter?.let { character ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(){
                    Column(modifier = Modifier.weight(1.5f)) {
                        Text(text = "${character.name} ", style = MaterialTheme.typography.titleLarge)
                        CharacterPortrait(character)
                        Text(text = "${character.rolClass} | ${character.race} | ${character.level} ", style = MaterialTheme.typography.titleMedium)

                    }

                    Column(modifier = Modifier.weight(2.0f)) {
                        HitPointsBar(
                            label = "HP",
                            maxValue = character.hp,
                            localValue = character.currentHp,
                            onValueChanged = { character.copy(currentHp = it) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        HitPointsBar(
                            label = "AP",
                            maxValue = character.ap,
                            localValue = character.currentAp,
                            onValueChanged = { character.copy(currentAp = it) }
                        )
                    }
                }


        }


            CharacterMenu()

            // CAMPOS NUMÉRICOS Y STATS
            StatSection(
                editableCharacter = character,
                onCharacterChange = {
                    characterViewModel.updateCharacter(it)
                }
            )


            SkillSection(
                editableCharacter = character,
            )
        }
    }
}
