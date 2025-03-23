package com.unir.character.ui.screens.spells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.character.data.model.local.Spell
import com.di.LocalCharacterViewModel
import com.ui.components.RegularCard
import com.ui.components.buttons.BackButton
import com.ui.layout.MainLayout
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.character.viewmodels.SpellViewModel


@Composable
fun CharacterSpellScreen(){
    MainLayout(){
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            CharacterSpellBody()
            BackButton()
        }
    }
}


@Composable
fun CharacterSpellBody(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    spellViewModel: SpellViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val spellList by spellViewModel.spellList.observeAsState()
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()
    spellViewModel.getSpellsForCharacter(currentCharacter!!)

    if (spellList == null) {
        Text("Cargando Hechizos...")
    } else {
        var spells by remember { mutableStateOf(spellList!!) }
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            spells.forEach { spell ->
                SpellCard(spell = spell)
            }
        }


    }
}


@Composable
fun SpellCard(spell: Spell, modifier: Modifier = Modifier) {
    RegularCard(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = spell.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Daño: ${spell.description}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Nivel: ${spell.level}",
                style = MaterialTheme.typography.bodyMedium
            )

            // BOTÓN PARA TIRAR EL DADO Y ACTIVAR EFECTO
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ver detalles")
            }
        }
    }
}
