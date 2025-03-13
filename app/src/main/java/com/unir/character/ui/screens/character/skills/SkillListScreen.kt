package com.unir.character.ui.screens.character.skills

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.character.data.model.local.Skill
import com.di.LocalCharacterViewModel
import com.unir.character.ui.screens.components.BackButton
import com.unir.character.ui.screens.components.RegularCard
import com.unir.character.ui.screens.layout.MainLayout
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.character.viewmodels.SkillViewModel


@Composable
fun SkillListScreen(){
    MainLayout(){
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            SkillListBody()
            BackButton()
        }
    }
}


@Composable
fun SkillListBody(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    skillViewModel: SkillViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()
    val spellList by skillViewModel.skillList.collectAsState()
    skillViewModel.getAllSKills()
    //skillViewModel.getSkillsFromCharacter(currentCharacter!!)

    if (spellList == null) {
        Text("Cargando Hechizos...")
    } else {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                skillViewModel.getSkillsFromCharacter(currentCharacter!!)
            }) {
                Text(text = "Obtener habilidades")
            }
            spellList.forEach { spell ->
                SkillCard(skill = spell)
            }
        }


    }
}


@Composable
fun SkillCard(skill: Skill, modifier: Modifier = Modifier) {
    RegularCard(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Daño: ${skill.description}",
                style = MaterialTheme.typography.bodyMedium
            )


            // BOTÓN PARA AÑADIR A LA LISTA DE SKILLS DEL PERSONAJE
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ver detalles")
            }
        }
    }
}