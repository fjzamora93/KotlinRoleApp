package com.unir.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.Skill
import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.character.ui.viewmodels.SkillViewModel
import com.unir.roleapp.R
import com.unir.roleapp.character.data.model.local.StatName
import kotlinx.coroutines.launch


@Composable
fun SkillSectionBody(
    skillList : List<SkillValue>,
) {

    // Estado local sincronizado con el ViewModel
    var localSkillList by remember(skillList) {
        mutableStateOf(skillList.toMutableList())
    }

    // Obtener todas las etiquetas de habilidades únicas
    val skillTags = localSkillList.map { it.skill.tag }.distinct()
    var selectedTag by remember { mutableStateOf<StatName?>(StatName.STRENGTH) }

    // Crear un map con claves = tags y valores = lista de habilidades por cada tag
    val skillsByTag = skillTags.associateWith { tag ->
        localSkillList.filter { it.skill.tag == tag }
    }

    // Control de scroll
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val columnPositions = remember { mutableMapOf<StatName, Int>() }
    Column() {

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
                if (tag != StatName.COMBAT) {
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
                                StatName.DEXTERITY    -> Icons.Default.DirectionsRun
                                StatName.INTELLIGENCE -> Icons.Default.School
                                StatName.CHARISMA     -> Icons.Default.Face
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
        }

        // Body de la columna Listado de habilidades con Scroll horizontal
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            skillTags.forEach { tag ->
                if (tag != StatName.COMBAT){
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(12.dp)
                            .onGloballyPositioned { coordinates ->
                                columnPositions[tag] = coordinates.positionInParent().x.toInt()
                            },
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Habilidades de ${StatName.getString(tag)}",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        skillsByTag[tag]?.forEach { skill ->
                            SkillDetail(
                                skill = skill.skill,
                                localValue= skill.value,
                                value = skill.value,
                                skillName = skill.skill.tag
                            )

                        }
                    }
                }
            }
        }
    }

}



@Composable
fun SkillDetail(
    skill: Skill,
    value: Int,
    localValue: Int = 0,
    skillName: StatName,

    itemViewModel: ItemViewModel = hiltViewModel()

) {
    val modifyingStats = itemViewModel.modifyingStats.collectAsState()
    val modifiedValue  by remember { mutableIntStateOf( modifyingStats.value.find { it.type == skillName }?.modifyingValue ?: 0 ) }
    val displayValue by remember { mutableIntStateOf(localValue + modifiedValue) }

    val borderColor = when {
        modifiedValue > 0 -> Color(0xFF4CAF50) // Verde
        modifiedValue < 0 -> Color(0xFFF44336) // Rojo
        else -> MaterialTheme.colorScheme.outline
    }

    val arrowPainter = when {
        modifiedValue > 0 -> painterResource(id = R.drawable.baseline_keyboard_double_arrow_up_24)
        modifiedValue < 0 -> painterResource(id = R.drawable.baseline_keyboard_double_arrow_down_24)
        else -> null
    }

    val arrowColor = when {
        modifiedValue > 0 -> Color(0xFF4CAF50)
        modifiedValue < 0 -> Color(0xFFF44336)
        else -> Color.Transparent
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            arrowPainter?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = arrowColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp, 32.dp)
                    .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayValue.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }


        }

        // Texto del nombre
        Text(
            text = skill.name.split("(")[0],
            style = MaterialTheme.typography.bodyMedium
        )
    }

}
