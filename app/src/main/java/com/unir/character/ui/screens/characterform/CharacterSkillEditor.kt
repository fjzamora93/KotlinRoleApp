package com.unir.character.ui.screens.characterform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.ui.screens.common.InlineProgressBar


@Composable
fun SkillSectionForm(
    character: CharacterEntity,
    onValueChange: (CharacterEntity) -> Unit
) {

    val habilidadesFisicas = listOf("Atletismo", "Artesanía", "Supervivencia")
    val habilidadesMentales = listOf("Concentración", "Investigación", "Memoria")
    val habilidadesSociales = listOf("Persuasión", "Engaño", "Liderazgo")
    val habilidadesArmas = listOf("Arco", "Espada", "Dagas")

    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Habilidades físicas",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        habilidadesFisicas.forEach { habilidad ->
            InlineProgressBar(
                label = habilidad,
                maxValue = 20,
                localValue = character.currentHp,
                onValueChanged = { newValue ->
                    onValueChange(character.copy(currentHp = newValue))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Habilidades Mentales",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        habilidadesMentales.forEach { habilidad ->
            InlineProgressBar(
                label = habilidad,
                maxValue = 20,
                localValue = character.currentHp,
                onValueChanged = { newValue ->
                    onValueChange(character.copy(currentHp = newValue))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Habilidades Sociales",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        habilidadesSociales.forEach { habilidad ->
            InlineProgressBar(
                label = habilidad,
                maxValue = 20,
                localValue = character.currentHp,
                onValueChanged = { newValue ->
                    onValueChange(character.copy(currentHp = newValue))
                }
            )
        }


    }
}