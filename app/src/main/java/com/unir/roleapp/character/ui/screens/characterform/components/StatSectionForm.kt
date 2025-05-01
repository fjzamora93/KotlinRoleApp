package com.unir.roleapp.character.ui.screens.characterform.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.core.ui.components.error.ErrorCard


@Composable
fun StatSectionForm(
    character: CharacterEntity,
    onValueChange: (CharacterEntity) -> Unit,
    isValid: (Boolean) -> Unit
) {

    // La suma de todos los stats debe ser Igual a 72
    var statsSum = character.strength +
            character.dexterity +
            character.constitution +
            character.intelligence +
            character.wisdom +
            character.charisma

    var isValidState = remember(character) { statsSum == 72 }
    LaunchedEffect(isValidState) {
        isValid(isValidState)
    }

    LaunchedEffect(character) {
        statsSum = character.strength +
                character.dexterity +
                character.constitution +
                character.intelligence +
                character.wisdom +
                character.charisma

        isValid(statsSum == 72)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()){

        Text(
            "Atributos",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(24.dp),
            color = MaterialTheme.colorScheme.inverseOnSurface
        )

        if (!isValidState){

            if (statsSum > 72) {
                ErrorCard(errorMessage = "¡Límite de puntos superado! Exceso de ${statsSum - 72} puntos")
            }

            if (statsSum < 72) {
                ErrorCard(errorMessage = "Aún te quedan ${72 - statsSum} puntos para repartir")
            }
        }


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
        HorizontalDivider()
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
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.inverseOnSurface)

        NumberPicker(
            value = value,
            range = 1..20, // Rango de 1 a 20
            onValueChange = onValueChange,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.inverseOnSurface)
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}


