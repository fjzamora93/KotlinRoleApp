package com.roleapp.character.ui.screens.spells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.character.data.model.local.Spell
import com.roleapp.character.ui.screens.common.BottomDialogueMenu
import com.roleapp.character.ui.screens.common.layout.CharacterLayout
import com.roleapp.character.ui.screens.items.components.CharacterInventoryBody
import com.roleapp.core.ui.components.buttons.BackButton
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.SpellViewModel
import com.roleapp.core.ui.components.common.CustomCircularProgressIndicator
import com.unir.roleapp.character.ui.screens.characterSheet.CharacterSection
import com.unir.roleapp.character.ui.screens.spells.components.SpellSummaryComponent
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation





@Composable
fun CharacterSpellBody(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    spellViewModel: SpellViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val spellList by spellViewModel.spellList.observeAsState()
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()

    LaunchedEffect(currentCharacter) {
        characterViewModel.getActiveCharacter()
        currentCharacter?.let { character ->
            spellViewModel.getSpellsForCharacter(character)
        }
    }


    if (spellList == null) {
        CrossSwordsAnimation()
    } else {
        val spells by remember { mutableStateOf(spellList) }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            spells?.forEach { spell ->
                SpellSummaryComponent(
                    spell = spell,
                    onClick = { /* Lógica para manejar el clic en el ítem */ }
                )
                HorizontalDivider()
            }
        }

    }
}

