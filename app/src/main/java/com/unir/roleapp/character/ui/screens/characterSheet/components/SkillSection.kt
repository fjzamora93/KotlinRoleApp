package com.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.ui.screens.common.InlineStat
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.SkillViewModel


@Composable
fun SkillSection(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    ){
    val character by characterViewModel.selectedCharacter.collectAsState()
    var isEditing by remember { mutableStateOf(false) }


    character?.let {
        Column(){
            if (!isEditing) {
                SkillSectionBody(
                    editableCharacter = it,
                    onEdit = { isEditing = !isEditing }
                )

            } else {
                SkillForm(
                    character = it,
                    onEdit = { isEditing = !isEditing  }
                )
            }
        }

    }
}



@Composable
fun SkillSectionBody(
    skillViewModel: SkillViewModel = hiltViewModel(),
    editableCharacter: CharacterEntity,
    onEdit: () -> Unit = {},
) {
    skillViewModel.getSkillsFromCharacter(editableCharacter)
    val skillList by skillViewModel.skillList.collectAsState()
    val pointsAvailable by skillViewModel.pointsAvailable.collectAsState()



    LaunchedEffect(editableCharacter, skillList) {
        skillViewModel.validateSkills(editableCharacter, skillList)
    }

    LaunchedEffect(editableCharacter) {
        skillViewModel.getSkillsFromCharacter(editableCharacter)
    }


    DefaultRow {
        Text(
            text = "Puntos disponibles: $pointsAvailable",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "settings",
            Modifier
                .size(30.dp)
                .clickable { onEdit() }
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (skillList.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(onClick = { skillViewModel.getSkillsFromCharacter(editableCharacter) }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }

        }

        // Columna SGTR
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(text = "Físicas", style= MaterialTheme.typography.titleSmall)

            skillList.filter { it.skill.tag == "STR" }.forEachIndexed() { index, skill ->
                InlineStat(
                    localValue = skill.value,
                    label = skill.skill.name.split("(")[0],
                )

            }
        }

        // Columna DES
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(text = "Habilidad", style= MaterialTheme.typography.titleSmall)

            skillList.filter { it.skill.tag == "DEX" }.forEach { skill ->
                InlineStat(
                    localValue = skill.value,
                    label = skill.skill.name.split("(")[0],
                )
            }
        }
    }

    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        thickness = 1.dp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // COLUMNA INT
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(text = "Conocimiento", style= MaterialTheme.typography.titleSmall)
            skillList.filter { it.skill.tag == "INT" }.forEachIndexed { index, skill ->
                Row(verticalAlignment = Alignment.CenterVertically){


                    InlineStat(
                        localValue = skill.value,
                        label = skill.skill.name.split("(")[0],
                    )


                }
            }
        }


        // Columna CHA
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        ) {
            Text(text = "Sociales", style= MaterialTheme.typography.titleSmall)
            skillList.filter { it.skill.tag == "CHA" }.forEach { skill ->
                InlineStat(
                    localValue = skill.value,
                    label = skill.skill.name.split("(")[0],
                )
            }
        }
    }

}




