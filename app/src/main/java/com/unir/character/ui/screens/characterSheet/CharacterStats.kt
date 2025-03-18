package com.unir.character.ui.screens.characterform.haracterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.unir.character.data.model.local.CharacterEntity


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



@Composable
fun NumberBox(
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "+1",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp).width(10.dp))

}
