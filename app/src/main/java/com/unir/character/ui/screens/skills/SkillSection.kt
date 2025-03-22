package com.unir.character.ui.screens.skills

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ui.components.DefaultRow
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.ui.screens.common.InlineStat
import com.unir.character.ui.screens.dialogues.CharacterDialog
import com.unir.character.viewmodels.ItemViewModel
import com.unir.character.viewmodels.SkillViewModel


@Composable
fun SkillSection(
    skillViewModel: SkillViewModel = hiltViewModel(),
    editableCharacter: CharacterEntity
) {
    skillViewModel.getSkillsFromCharacter(editableCharacter)
    val skillList by skillViewModel.skillList.collectAsState()

    var isEditing by remember { mutableStateOf(false) }

    val pointsAvailable by skillViewModel.pointsAvailable.collectAsState()

    DefaultRow {

        Text(
            text = "Puntos de habilidad disponibles: $pointsAvailable",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )


        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Distribuir habilidades",
            Modifier
                .size(30.dp)
                .clickable { isEditing = !isEditing }
        )

    }

    if (!isEditing){
        DefaultRow {
            Column{
                skillList.forEach { skill ->
                    DefaultRow {
                        InlineStat(
                            localValue = skill.value,
                            label = skill.skill.name,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

    } else {
        SkillForm(
            skillList = skillList, character = editableCharacter,
            onConfirm = { isEditing = !isEditing  }
        )
    }

}




@Composable
fun SkillItem(
    skillName: String,
    skillValue: Int,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Círculo con el número
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = skillValue.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre el círculo y la barra

        // Barra de progreso con el nombre de la habilidad
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp) // Altura de la barra
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = skillName,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

