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
import androidx.compose.ui.graphics.Color
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

    // Estado local para edición temporal
    var localEdits by remember { mutableStateOf<Map<Int, Int>?>(null) }

    LaunchedEffect(skillList) {
        localEdits = null // Resetear ediciones cuando el ViewModel actualiza la lista
    }

    Column {
        // Mostrar puntos disponibles y errores
        errorMessage?.let { Text(it, color = Color.Red) }

        // Botón de guardado
        if (isValid) {
            Button(onClick = {
                skillViewModel.updateSkills(character, skillList)
                onConfirm()
            }) {
                Text("Guardar")
            }
        }

        // Lista de habilidades
        skillList.forEachIndexed { index, skill ->
            val displayValue = localEdits?.get(index) ?: skill.value

            InlineProgressBar(
                label = skill.skill.name,
                minValue = 5,
                maxValue = 15, // Asegurar que coincide con tus reglas de validación
                limit = pointsAvailable,
                localValue = displayValue,
                onValueChanged = { newValue ->
                    // 1. Guardar cambio temporal
                    localEdits = (localEdits ?: emptyMap()) + (index to newValue.coerceIn(5, 15))

                    // 2. Crear lista temporal con cambios
                    val tempList = skillList.mapIndexed { idx, s ->
                        if (localEdits?.containsKey(idx) == true) s.copy(value = localEdits!![idx]!!)
                        else s
                    }

                    // 3. Validar
                    skillViewModel.validateSkills(character, tempList)
                }
            )
        }
    }
}