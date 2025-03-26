package com.unir.character.ui.screens.characterform.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.unir.character.data.model.local.CharacterEntity


@Composable
fun StatSectionForm(
    character: CharacterEntity,
    onValueChange: (CharacterEntity) -> Unit
) {
    var characterToUpdate by remember(character) { mutableStateOf(character.copy()) }
    Column(modifier = Modifier.fillMaxWidth()){


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyNumberPicker(
                label = "Carisma",
                value = character.charisma,
                onValueChange = { newValue ->
                    onValueChange(character.copy(charisma = newValue))
                }
            )

            MyNumberPicker(
                label = "Inteligencia",
                value = character.intelligence,
                onValueChange = { newValue ->
                    onValueChange(character.copy(intelligence = newValue))
                }
            )

            MyNumberPicker(
                label = "Sabiduría",
                value = character.wisdom,
                onValueChange = { newValue ->
                    onValueChange(character.copy(wisdom = newValue))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyNumberPicker(
                label = "Fuerza",
                value = character.strength,
                onValueChange = { newValue ->
                    onValueChange(character.copy(strength = newValue))
                }
            )

            MyNumberPicker(
                label = "Destreza",
                value = character.dexterity,
                onValueChange = { newValue ->
                    onValueChange(character.copy(dexterity = newValue))
                }
            )

            MyNumberPicker(
                label = "Constitución",
                value = character.constitution,
                onValueChange = { newValue ->
                    onValueChange(character.copy(constitution = newValue))
                }
            )
        }
    }
}



@Composable
fun MyNumberPicker(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier:Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)

        NumberPicker(
            value = value,
            range = 1..20, // Rango de 1 a 20
            onValueChange = onValueChange
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}


