package com.unir.character.ui.screens.characterSheet.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
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
import com.unir.character.ui.screens.common.InlineProgressBar
import com.unir.character.viewmodels.SkillViewModel
import kotlinx.coroutines.launch

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

    // Obtener todas las etiquetas de habilidades únicas
    val skillTags = localSkillList.map { it.skill.tag }.distinct()

    // Crear un map con claves = tags y valores = lista de habilidades por cada tag
    val skillsByTag = skillTags.associateWith { tag ->
        localSkillList.filter { it.skill.tag == tag }
    }

    // Control de scroll
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val columnPositions = remember { mutableMapOf<String, Int>() }
    Column {
        errorMessage?.let { Text(it, color = Color.Red) }

        // Botones de navegación por tags
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            skillTags.forEach { tag ->
                Button(onClick = {
                    coroutineScope.launch {
                        columnPositions[tag]?.let { position ->
                            scrollState.animateScrollTo(position)
                        }
                    }
                }) {
                    Text(tag)
                }
            }
        }

        // Botón Guardar
        if (isValid) {
            Button(onClick = {
                skillViewModel.updateSkills(character, localSkillList)
                onConfirm()
            }) {
                Text("Guardar")
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
                    modifier = Modifier
                        .padding(8.dp)
                        .onGloballyPositioned { coordinates ->
                            columnPositions[tag] = coordinates.positionInParent().x.toInt()
                        },
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        text = "Habilidades de $tag",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

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
    skill: Skill, // Suponiendo que SkillEntity tiene skill.name y value
    value: Int,
    onValueChanged: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(skill.name, style = MaterialTheme.typography.bodyMedium)

        Button(
            onClick = { if (value > 5) onValueChanged(value - 1) }, // Límite mínimo en 5
            enabled = value > 5
        ) {
            Text("-")
        }

        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { if (value < 15) onValueChanged(value + 1) }, // Límite máximo en 15
            enabled = value < 15
        ) {
            Text("+")
        }
    }
}
