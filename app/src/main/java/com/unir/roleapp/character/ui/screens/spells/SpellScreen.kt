package com.roleapp.character.ui.screens.spells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.SpellViewModel
import com.unir.roleapp.character.ui.screens.spells.components.SpellByLevelSection
import com.unir.roleapp.core.ui.components.SearchBar
import com.unir.roleapp.character.ui.screens.spells.components.SpellSummaryComponent
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation





@Composable
fun CharacterSpellBody(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    spellViewModel: SpellViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val spellList by spellViewModel.spellList.observeAsState()
    var filteredSpells by remember { mutableStateOf (spellList) }

    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()

    LaunchedEffect(currentCharacter) {
        currentCharacter?.let { character ->
            spellViewModel.getSpellsForCharacter(character)
        }
    }
    LaunchedEffect(spellList) {
        filteredSpells = spellList
    }

    SearchBar(onSearch = { searchText ->
        filteredSpells = spellList?.filter { spell ->
            spell.name.contains(searchText, ignoreCase = true) || spell.description.contains(searchText, ignoreCase = true)
        }
        if (filteredSpells?.isEmpty() == true || filteredSpells == null) {
            filteredSpells = spellList
        }
    })

    if (spellList == null) {
        CrossSwordsAnimation()
    } else {

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            filteredSpells?.let {
                SpellByLevelSection(filteredSpells = it.filter{ spell -> spell.level == 0 }, filter = "Trucos")
                SpellByLevelSection(filteredSpells = it.filter{ spell -> spell.level == 1 }, filter = "1")
                SpellByLevelSection(filteredSpells = it.filter{ spell -> spell.level == 2 }, filter = "2")
                SpellByLevelSection(filteredSpells = it.filter{ spell -> spell.level == 3 }, filter = "3")
                SpellByLevelSection(filteredSpells = it.filter{ spell -> spell.level == 4 }, filter = "4")
                SpellByLevelSection(filteredSpells = it.filter{ spell -> spell.level == 5 }, filter = "5")
                SpellByLevelSection(filteredSpells = it.filter{ spell -> spell.level == 5 }, filter = "6")
            }

        }
    }
}

