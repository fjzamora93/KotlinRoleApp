package com.unir.character.ui.screens.characterSheet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.ui.components.DefaultRow
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.SkillValue
import com.unir.character.ui.screens.common.InlineProgressBar
import com.unir.character.viewmodels.SkillViewModel



@Composable
fun SkillForm(
    skillViewModel: SkillViewModel = hiltViewModel(),
    skillList: List<SkillValue>,
    character: CharacterEntity,
    onConfirm : () -> Unit
) {

    var localSkillList by remember { mutableStateOf(skillList.toMutableList()) }
    val isValid by skillViewModel.isValid.collectAsState()

    // Validar automáticamente cuando cambie la lista de habilidades
    LaunchedEffect(localSkillList) {
        skillViewModel.validateSkills(character, localSkillList)
    }

    Column {
        DefaultRow {
            if (isValid) {
                Button(onClick = {
                    skillViewModel.updateSkills(character, localSkillList)
                    onConfirm()
                }) {
                    Text(text = "Guardar")
                }
            }
        }

        Text(
            text = "Habilidades",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Mostrar las habilidades con InlineProgressBar
        localSkillList.forEachIndexed { index, skill ->

            InlineProgressBar(
                label = skill.skill.name,
                maxValue = 20,
                localValue = skill.value,
                onValueChanged = { newValue ->
                    // Actualizar el valor de la habilidad en la lista local
                    localSkillList = localSkillList.toMutableList().apply {
                        this[index] = skill.copy(value = newValue)
                    }
                }
            )
        }
    }
}