package com.unir.character.ui.screens.skills

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.di.LocalCharacterViewModel
import com.ui.components.DefaultRow
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Skill
import com.unir.character.data.model.local.SkillValue
import com.unir.character.ui.screens.common.InlineProgressBar
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.character.viewmodels.SkillViewModel




@Composable
fun SkillSectionForm(
    skillViewModel: SkillViewModel = hiltViewModel(),
    skillList : List<SkillValue>,
    character: CharacterEntity
) {


    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Habilidades físicas",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        skillList.forEach { skill ->
            InlineProgressBar(
                label = skill.skill.name,
                maxValue = 20,
                localValue = skill.value,
                onValueChanged = { newValue ->
                    skillViewModel.validateSkills(character, skill)
                }
            )
        }


    }
}