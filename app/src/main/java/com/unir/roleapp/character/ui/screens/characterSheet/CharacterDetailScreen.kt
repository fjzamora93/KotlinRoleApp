package com.roleapp.character.ui.screens.characterSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.core.ui.layout.MenuOption
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.ui.screens.characterSheet.components.CombatSkillSection
import com.roleapp.character.ui.screens.characterSheet.components.SkillSection
import com.roleapp.character.ui.screens.characterSheet.components.StatSection
import com.roleapp.character.ui.screens.common.ProgressBarSection
import com.roleapp.character.ui.screens.common.dialogues.CharacterDialog
import com.unir.roleapp.character.ui.screens.common.InfoDialog
import com.roleapp.character.ui.screens.common.layout.CharacterLayout
import com.roleapp.character.ui.screens.items.components.CharacterInventoryBody
import com.roleapp.character.ui.screens.spells.CharacterSpellBody
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.R
import com.unir.roleapp.character.ui.screens.characterSheet.CharacterSection
import com.unir.roleapp.character.ui.screens.characterSheet.helper.util.calculateInitative
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation

@Composable
fun CharacterDetailScreen(
    characterId : Long,
    characterViewModel: CharacterViewModel = hiltViewModel(),
){

    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    var currentSection by remember { mutableStateOf(CharacterSection.CHARACTERDETAIL) }

    LaunchedEffect(characterId) {
        characterViewModel.getCharacterById(characterId)
    }

    CharacterLayout {
        selectedCharacter?.let { character ->
            when (currentSection) {
                CharacterSection.CHARACTERDETAIL -> DetailCharacterBody(
                    character = character,
                    onClick = { currentSection = it }
                )

                CharacterSection.INVENTORY -> CharacterInventoryBody()

                CharacterSection.SPELLS -> CharacterSpellBody()

                else -> DetailCharacterBody(
                    character = character,
                    onClick = { currentSection = it }
                )
            }

        } ?: CrossSwordsAnimation()
    }

}


@Composable
fun DetailCharacterBody(
    character: CharacterEntity,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    itemViewModel: ItemViewModel = hiltViewModel(),
    onClick : (CharacterSection) -> Unit = {}
) {
    var activeDialog by remember { mutableStateOf<CharacterDialog?>(null) }
    var characterState by remember { mutableStateOf(character) }
    val currentArmor by itemViewModel.armor.collectAsState()

    StatSection(
        editableCharacter = character,
        onCharacterChange = {
        }
    )
    HorizontalDivider(Modifier.padding(16.dp))


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1.5f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Armadura (icono a la izquierda, número y texto a la derecha)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Security, contentDescription = "Armadura",
                    Modifier
                        .size(60.dp)
                        .clickable { activeDialog = CharacterDialog.Armour },
                    tint = Color.LightGray,

                    )
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = currentArmor.toString(), style = MaterialTheme.typography.headlineMedium, color = Color.LightGray)
                    Text(text = "Armadura", fontSize = 12.sp, color = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // INICIATIVA
            Row(verticalAlignment = Alignment.CenterVertically) {
                var randomInitiative = calculateInitative(characterState.dexterity)

                Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = "Iniciativa",
                    Modifier
                        .size(60.dp)
                        .clickable {
                            activeDialog = CharacterDialog.Initiative
                            randomInitiative = calculateInitative(characterState.dexterity)
                                   },
                    tint = Color.LightGray,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "${randomInitiative.toString()}", fontSize = 20.sp, color = Color.LightGray)
                    Text(text = "Iniciativa", fontSize = 12.sp, color = Color.LightGray)

                }
            }
        }
        Column(
            modifier = Modifier.weight(2.0f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ProgressBarSection(character = characterState) { updatedCharacter ->
                characterState = updatedCharacter
                characterViewModel.saveCharacter(updatedCharacter)
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        MenuOption(
            text = stringResource(id = R.string.inventory),
            onClick = { onClick(CharacterSection.INVENTORY) },
            icon = Icons.Default.Apps,
            textColor = Color.LightGray,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CustomColors.BlackGradient)
                .clickable { onClick(CharacterSection.INVENTORY) }
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        MenuOption(
            text = stringResource(id = R.string.spells),
            onClick = { onClick(CharacterSection.SPELLS)  },
            icon = Icons.Default.MenuBook,
            textColor = Color.LightGray,

            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CustomColors.BlackGradient)
                .clickable { onClick(CharacterSection.SPELLS) }
                .padding(12.dp)
        )
    }

    // Sección de competencia con armas
    CombatSkillSection()

    HorizontalDivider(Modifier.padding(16.dp))

    SkillSection()

    HorizontalDivider(Modifier.padding(16.dp))


    activeDialog?.let {
        InfoDialog(
            activeDialog = it,
            onDismiss = { activeDialog = null }
        )
    }
}

