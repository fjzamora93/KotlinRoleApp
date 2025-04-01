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


@Composable
fun StatSection(
    editableCharacter: CharacterEntity,
    onCharacterChange: (CharacterEntity) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)){

        Row(verticalAlignment = Alignment.CenterVertically) {

            NumberBox(
                modifier = Modifier.weight(1f),
                label = "Fuerza",
                value = editableCharacter.strength,
            )

            NumberBox(
                modifier = Modifier.weight(1f),
                label = "Destreza",
                value = editableCharacter.dexterity,
            )

            NumberBox(
                modifier = Modifier.weight(1f),
                label = "Constitucion",
                value = editableCharacter.constitution,
            )
        }

        Spacer(modifier = Modifier.height(16.dp).width(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            NumberBox(
                modifier = Modifier.weight(1f),
                label = "Inteligencia",
                value = editableCharacter.intelligence,
            )
            NumberBox(
                modifier = Modifier.weight(1f),
                label = "Sabiduría",
                value = editableCharacter.wisdom,
            )

            NumberBox(
                modifier = Modifier.weight(1f),
                label = "Carisma",
                value = editableCharacter.charisma,
            )

        }
    }
}


