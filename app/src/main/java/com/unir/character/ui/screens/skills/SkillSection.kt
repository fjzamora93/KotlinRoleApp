package com.unir.character.ui.screens.skills

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.viewmodels.ItemViewModel
import com.unir.character.viewmodels.SkillViewModel


@Composable
fun SkillSection(
    skillViewModel: SkillViewModel = hiltViewModel(),
    editableCharacter: CharacterEntity
) {
    skillViewModel.getSkillsFromCharacter(editableCharacter)
    val skillList by skillViewModel.skillList.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Habilidades",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        skillList.forEach{
            skill ->
                SkillItem(skillName = skill.name, skillValue = 11)
                Spacer(modifier = Modifier.height(10.dp))
        }
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
                .size(48.dp) // Tamaño del círculo
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

