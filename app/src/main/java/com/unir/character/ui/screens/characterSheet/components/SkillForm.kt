package com.unir.character.ui.screens.characterSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Skill
import com.unir.character.data.model.local.SkillTags
import com.unir.character.data.model.local.tagToString
import com.unir.character.ui.screens.common.InlineProgressBar
import com.unir.character.ui.viewmodels.SkillViewModel
import com.unir.core.ui.components.common.DefaultRow
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun SkillForm(
    skillViewModel: SkillViewModel = hiltViewModel(),
    character: CharacterEntity,
    onEdit: () -> Unit
) {
    val skillList by skillViewModel.skillList.collectAsState()
    val pointsAvailable by skillViewModel.pointsAvailable.collectAsState()
    val errorMessage by skillViewModel.errorMessage.collectAsState()
    val isValid by skillViewModel.isValid.collectAsState()

    // Estado local sincronizado con el ViewModel
    var localSkillList by remember(skillList) {
        mutableStateOf(skillList.toMutableList())
    }

    // Obtener todas las etiquetas de habilidades únicas
    val skillTags = localSkillList.map { it.skill.tag }.distinct()
    var selectedTag by remember { mutableStateOf<String?>(SkillTags.STRENGTH ) }

    // Crear un map con claves = tags y valores = lista de habilidades por cada tag
    val skillsByTag = skillTags.associateWith { tag ->
        localSkillList.filter { it.skill.tag == tag }
    }

    // Control de scroll
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val columnPositions = remember { mutableMapOf<String, Int>() }
    Column(){

        DefaultRow {
            Text(
                text = "Puntos disponibles: $pointsAvailable",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // meter un enabled = isValid
            IconButton(
                onClick = {
                    skillViewModel.updateSkills(character, localSkillList)
                    onEdit()
                },
                enabled = isValid // deshabilitar boton si no está validado el formulario
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "save",
                    modifier = Modifier.size(30.dp)
                )
            }
        }


        errorMessage?.let { Text(it, color = Color.Red) }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
                .background(MaterialTheme.colorScheme.surface),
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
                    modifier = Modifier.size(48.dp).background(
                        if (selectedTag == tag) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        else Color.Transparent,
                    )
                ) {
                    Icon(
                        imageVector = when (tag) {
                            SkillTags.STRENGTH -> Icons.Default.FitnessCenter // Ícono de fuerza
                            SkillTags.DEXTERITY -> Icons.Default.DirectionsRun // Ícono de destreza
                            SkillTags.INTELLIGENCE -> Icons.Default.School // Ícono de conocimiento
                            SkillTags.CHARISMA -> Icons.Default.Face // Ícono de sociales
                            SkillTags.COMBAT -> Icons.Default.Security // Ícono de combate
                            else -> Icons.Default.Help // Ícono genérico
                        },
                        contentDescription = tagToString(tag),
                        modifier = Modifier.size(32.dp),
                        tint = if (selectedTag == tag) MaterialTheme.colorScheme.primary else Color.Gray
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
                        text = "Habilidades de ${tagToString(tag)}",
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
                                skillViewModel.validateSkills(character, localSkillList)
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
