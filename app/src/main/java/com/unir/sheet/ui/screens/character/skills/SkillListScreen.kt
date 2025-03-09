package com.unir.sheet.ui.screens.character.skills

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.sheet.data.model.Skill
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.components.RegularCard
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel
import com.unir.sheet.ui.viewmodels.SkillViewModel


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