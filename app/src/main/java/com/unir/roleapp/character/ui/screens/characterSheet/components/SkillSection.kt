package com.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.SkillViewModel
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.character.ui.screens.characterSheet.components.SkillSectionBody


@Composable
fun SkillSection(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    skillViewModel: SkillViewModel = hiltViewModel(),
    ){
    val character by characterViewModel.selectedCharacter.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    val pointsAvailable by skillViewModel.pointsAvailable.collectAsState()
    val skillList by skillViewModel.skillList.collectAsState()


    // Validación y actualización del formulario
    val isValid by skillViewModel.isValid.collectAsState()
    var updatedSkills by remember { mutableStateOf(skillList) }

    LaunchedEffect(character) {
        character?.let {
            skillViewModel.getSkillsFromCharacter(it)
        }
    }

    LaunchedEffect(skillList) {
        skillViewModel.validateSkills(character!!, skillList)
    }

    LaunchedEffect(updatedSkills) {
        skillViewModel.validateSkills(character!!, updatedSkills)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        ) {
        IconButton(
            enabled =  if (isEditing) isValid else true,
            onClick = {
                if (isEditing && isValid) {
                    skillViewModel.updateSkills(character!!, updatedSkills)
                }
                isEditing = !isEditing
            },
        ) {
            Icon(
                imageVector = if (isEditing) Icons.Default.Save else Icons.Default.Settings,
                contentDescription = "setting",
                modifier = Modifier.size(30.dp),

            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = if (!isEditing) "Repartir puntos de habilidad" else "Puntos disponibles: $pointsAvailable",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color.LightGray
        )
    }

    character?.let { it ->
        Column(){
            if (!isEditing) {
                SkillSectionBody(skillList)

            } else {
                SkillForm(
                    character = it,
                    onChanges = { newSkills ->
                        updatedSkills = newSkills
                        skillViewModel.validateSkills(character!!, updatedSkills)
                    }
                )
            }
        }

    }
}





