package com.roleapp.character.ui.screens.characterSheet

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.core.ui.components.common.CustomCircularProgressIndicator
import com.roleapp.core.ui.layout.MenuOption
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.ui.screens.characterSheet.components.CombatSkillSection
import com.roleapp.character.ui.screens.characterSheet.components.SkillSection
import com.roleapp.character.ui.screens.characterSheet.components.StatSection
import com.roleapp.character.ui.screens.common.ProgressBarSection
import com.roleapp.character.ui.screens.common.dialogues.CharacterDialog
import com.roleapp.character.ui.screens.common.dialogues.SwitchDialogue
import com.roleapp.character.ui.screens.common.layout.CharacterLayout
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import kotlinx.coroutines.launch

@Composable
fun CharacterDetailScreen(
    characterId : Long,
    characterViewModel: CharacterViewModel = hiltViewModel(),
){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()

    // Obtener datos cuando se monta la pantalla
    LaunchedEffect(characterId) {
        characterViewModel.getCharacterById(characterId)
    }


    CharacterLayout { onClickDrawer ->
        selectedCharacter?.let { character ->
            DetailCharacterBody(
                character = character,
                onClick = { onClickDrawer() }
            )
        } ?: CustomCircularProgressIndicator()
    }


}







@Composable
fun DetailCharacterBody(
    character: CharacterEntity,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    onClick : () -> Unit = {}
) {
    var activeDialog by remember { mutableStateOf<CharacterDialog?>(null) }
    var characterState by remember { mutableStateOf(character) }
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
                Icon(imageVector = Icons.Outlined.Security, contentDescription = "Armadura",
                    Modifier
                        .size(60.dp)
                        .clickable { activeDialog = CharacterDialog.Armour })
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "15", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Armadura", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // INICIATIVA
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = "Iniciativa",
                    Modifier
                        .size(60.dp)
                        .clickable { activeDialog = CharacterDialog.Initiative })
                Spacer(modifier = Modifier.width(8.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "+4", fontSize = 20.sp)
                    Text(text = "Iniciativa", fontSize = 12.sp)
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


    MenuOption(
        text = "Inventario, hechizos, Sesión",
        onClick = { onClick() },
        icon = Icons.Default.Apps,
    )

    CombatSkillSection()

    HorizontalDivider(Modifier.padding(16.dp))


    SkillSection()

    StatSection(
        editableCharacter = character,
        onCharacterChange = {
        }
    )

    activeDialog?.let {
        SwitchDialogue(
            activeDialog = it,
            onDismiss = { activeDialog = null }
        )
    }
}

