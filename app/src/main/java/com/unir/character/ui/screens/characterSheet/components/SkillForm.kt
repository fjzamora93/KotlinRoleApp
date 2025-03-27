package com.unir.character.ui.screens.characterSheet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ui.components.DefaultRow
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.SkillValue
import com.unir.character.ui.screens.common.InlineProgressBar
import com.unir.character.viewmodels.SkillViewModel

@Composable
fun SkillForm(
    skillViewModel: SkillViewModel = hiltViewModel(),
    character: CharacterEntity,
    onConfirm: () -> Unit
) {
    val skillList by skillViewModel.skillList.collectAsState()
    val pointsAvailable by skillViewModel.pointsAvailable.collectAsState()
    val errorMessage by skillViewModel.errorMessage.collectAsState()
    val isValid by skillViewModel.isValid.collectAsState()

    // Estado local sincronizado con el ViewModel
    var localSkillList by remember(skillList) {
        mutableStateOf(skillList.toMutableList())
    }

    Column {
        errorMessage?.let { Text(it, color = Color.Red) }

        // Mostrar puntos disponibles (añadido para feedback visual)
        Text("Puntos disponibles: $pointsAvailable",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp))

        if (isValid) {
            Button(onClick = {
                skillViewModel.updateSkills(character, localSkillList)
                onConfirm()
            }) {
                Text("Guardar")
            }
        }

        localSkillList.forEachIndexed { index, skill ->
            InlineProgressBar(
                label = skill.skill.name,
                minValue = 5,
                maxValue = 15,
                limit = pointsAvailable + (skillList[index].value - skill.value).coerceAtLeast(0),
                localValue = skill.value,
                onValueChanged = { newValue ->
                    // 1. Actualizar lista local
                    localSkillList = localSkillList.toMutableList().apply {
                        this[index] = this[index].copy(value = newValue)
                    }
                    // 2. Validar cambios (no guardar todavía)
                    skillViewModel.validateSkills(character, localSkillList)
                }
            )
        }
    }
}