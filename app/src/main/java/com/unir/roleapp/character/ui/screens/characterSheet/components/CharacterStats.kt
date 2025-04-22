package com.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.ui.screens.common.NumberBox
import com.unir.roleapp.character.data.model.local.StatName


@Composable
fun StatSection(
    editableCharacter: CharacterEntity,
    onCharacterChange: (CharacterEntity) -> Unit
) {
    Column(modifier = Modifier.padding(30.dp)){

        Row(verticalAlignment = Alignment.CenterVertically) {

            NumberBox(
                modifier = Modifier.weight(1f),
                stat = StatName.STRENGTH,
                value = editableCharacter.strength,
            )
            Spacer(modifier = Modifier.width(8.dp))

            NumberBox(
                modifier = Modifier.weight(1f),
                stat = StatName.DEXTERITY,
                value = editableCharacter.dexterity,
            )
            Spacer(modifier = Modifier.width(8.dp))

            NumberBox(
                modifier = Modifier.weight(1f),
                stat = StatName.CONSTITUTION,
                value = editableCharacter.constitution,
            )
        }

        Spacer(modifier = Modifier.height(16.dp).width(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberBox(
                modifier = Modifier.weight(1f),
                stat = StatName.INTELLIGENCE,
                value = editableCharacter.intelligence,
            )
            Spacer(modifier = Modifier.width(8.dp))

            NumberBox(
                modifier = Modifier.weight(1f),
                stat = StatName.WISDOM,
                value = editableCharacter.wisdom,
            )
            Spacer(modifier = Modifier.width(8.dp))

            NumberBox(
                modifier = Modifier.weight(1f),
                stat = StatName.CHARISMA,
                value = editableCharacter.charisma,
            )

        }
    }
}


