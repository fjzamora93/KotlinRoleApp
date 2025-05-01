package com.unir.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.character.data.model.local.Skill
import com.unir.roleapp.character.data.model.local.SkillValue

import com.unir.roleapp.character.ui.viewmodels.SkillViewModel
import com.unir.roleapp.core.ui.components.common.DefaultRow
import com.unir.roleapp.character.data.model.local.StatName
import kotlinx.coroutines.launch

@Composable
fun SkillForm(
    skillViewModel: SkillViewModel = hiltViewModel(),
    character: CharacterEntity,
    onChanges: (List<SkillValue>) -> Unit
) {
    val skillList by skillViewModel.skillList.collectAsState()
    val errorMessage by skillViewModel.errorMessage.collectAsState()

    // Estado local sincronizado con el ViewModel
    var localSkillList by remember(skillList) {
        mutableStateOf(skillList.toMutableList())
    }

    // Obtener todas las etiquetas de habilidades únicas
    val skillTags = localSkillList.map { it.skill.tag }.distinct()
    var selectedTag by remember { mutableStateOf<StatName?>(StatName.STRENGTH ) }

    // Crear un map con claves = tags y valores = lista de habilidades por cada tag
    val skillsByTag = skillTags.associateWith { tag ->
        localSkillList.filter { it.skill.tag == tag }
    }

    // Control de scroll
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val columnPositions = remember { mutableMapOf<StatName, Int>() }
    Column(){

        errorMessage?.let { Text(it, color = Color.Red) }

        // CABECERA DE LA COLUMNA (Con las subsecciones de la tabla)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            skillTags.forEach { tag ->
                    IconButton(
                        onClick = {
                            selectedTag = tag
                            coroutineScope.launch {
                                columnPositions[tag]?.let { position ->
                                    scrollState.animateScrollTo(position)
                                }
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                if (selectedTag == tag)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                else Color.Transparent,
                                shape = RoundedCornerShape(24.dp)
                            )
                    ) {
                        Icon(
                            imageVector = when (tag) {
                                StatName.STRENGTH     -> Icons.Default.FitnessCenter
                                StatName.DEXTERITY    -> Icons.Default.Handyman
                                StatName.INTELLIGENCE -> Icons.Default.School
                                StatName.CHARISMA     -> Icons.Default.Face
                                StatName.COMBAT         -> Icons.Default.Security
                                else                  -> Icons.Default.Help
                            },
                            contentDescription = StatName.getString(tag),
                            modifier = Modifier.size(32.dp),
                            tint = if (selectedTag == tag)
                                MaterialTheme.colorScheme.onPrimaryContainer
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

            }
        }

        // Secciones de habilidades con scroll horizontal
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            skillTags.forEach { tag ->
                Column(
                    modifier = Modifier.padding(8.dp)
                        .onGloballyPositioned { coordinates ->
                            columnPositions[tag] = coordinates.positionInParent().x.toInt()
                        },
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text = "Habilidades de ${StatName.getString(tag)}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    skillsByTag[tag]?.forEach { skill ->
                        SkillCounter(
                            skill = skill.skill,
                            value = skill.value,
                            onValueChanged = { newValue ->
                                localSkillList = localSkillList.toMutableList().apply {
                                    val index = indexOfFirst { it.skill == skill.skill }
                                    if (index != -1) {
                                        this[index] = this[index].copy(value = newValue)
                                    }
                                }

                                onChanges(localSkillList)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SkillCounter(
    skill: Skill,
    value: Int,
    onValueChanged: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.width(150.dp)
        ) {
            Text(
                text = skill.name.split("(")[0],
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column(
            modifier = Modifier.width(150.dp)
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(
                onClick = { if (value > 5) onValueChanged(value - 1) },
                enabled = value > 5,
                modifier = Modifier.size(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(0.dp) // <- Elimina el padding interno
            ) {
                Text("-", style = MaterialTheme.typography.bodyMedium)
            }

            Box(
                modifier = Modifier
                    .size(40.dp, 32.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            Button(
                onClick = { if (value < 15) onValueChanged(value + 1) },
                enabled = value < 15,
                modifier = Modifier.size(32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(0.dp) // <- Elimina el padding interno

            ) {
                Text("+", style = MaterialTheme.typography.bodyMedium)
            }
        }
        }
    }
}
