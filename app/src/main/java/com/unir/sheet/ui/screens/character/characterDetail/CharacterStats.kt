package com.unir.sheet.ui.screens.character.haracterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.ui.screens.components.MinusButton
import com.unir.sheet.ui.screens.components.PlusButton
import com.unir.sheet.ui.screens.components.RegularCard
import com.unir.sheet.util.MedievalColours


@Composable
fun StatSection(
    editableCharacter: CharacterEntity,
    onCharacterChange: (CharacterEntity) -> Unit
) {
    CharacterBar(
        label = "HP",
        maxValue = editableCharacter.hp,
        localValue = editableCharacter.currentHp,
        onValueChanged = { onCharacterChange(editableCharacter.copy(currentHp = it)) }
    )

    CharacterBar(
        label = "AP",
        color = MedievalColours.blueGradient,
        maxValue = editableCharacter.ap,
        localValue = editableCharacter.currentAp,
        onValueChanged = { onCharacterChange(editableCharacter.copy(currentAp = it)) }
    )

    RegularCard(){

        Column(horizontalAlignment = Alignment.CenterHorizontally ){
            Text(text = "General", style = MaterialTheme.typography.titleMedium)
            CharacterNumberField(
                label = "Nivel",
                value = editableCharacter.level,
                onValueChange = { onCharacterChange(editableCharacter.copy(level = it)) }
            )

            Text(text = "Stats", style = MaterialTheme.typography.titleMedium)
            CharacterNumberField(
                label = "Fuerza",
                value = editableCharacter.strength,
                onValueChange = { onCharacterChange(editableCharacter.copy(strength = it)) }
            )

            CharacterNumberField(
                label = "Destreza",
                value = editableCharacter.dexterity,
                onValueChange = { onCharacterChange(editableCharacter.copy(dexterity = it)) }
            )

            CharacterNumberField(
                label = "Constitucion",
                value = editableCharacter.constitution,
                onValueChange = { onCharacterChange(editableCharacter.copy(constitution = it)) }
            )

            CharacterNumberField(
                label = "Inteligencia",
                value = editableCharacter.intelligence,
                onValueChange = { onCharacterChange(editableCharacter.copy(intelligence = it)) }
            )
            CharacterNumberField(
                label = "Sabiduría",
                value = editableCharacter.wisdom,
                onValueChange = { onCharacterChange(editableCharacter.copy(wisdom = it)) }
            )

            CharacterNumberField(
                label = "Carisma",
                value = editableCharacter.charisma,
                onValueChange = { onCharacterChange(editableCharacter.copy(charisma = it)) }
            )
        }
        }


}

@Composable
fun CharacterNumberField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp)) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(end = 16.dp))
            MinusButton(
                localValue= value,
                onValueChanged = onValueChange,
                style = "Square"
            )

            Text(text = value.toString(), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(horizontal = 18.dp))

            PlusButton(
                localValue= value,
                onValueChanged = onValueChange,
                style = "Square"
            )


        }
    }
}


@Composable
fun CharacterBar(
    label: String = "",
    color: Brush = MedievalColours.greenGradient,
    maxValue: Int = 20,
    localValue: Int = 10,
    onValueChanged: (Int) -> Unit
) {
    // Calculamos el porcentaje de llenado de la barra en función de los valores proporcionados
    val progress = try {
        // Aseguramos que maxValue no sea cero antes de dividir
        if (maxValue != 0) {
            localValue.toFloat() / maxValue.toFloat()
        } else {
            1f  // Si maxValue es 0, asignamos un valor por defecto
        }
    } catch (e: ArithmeticException) {
        1f  // En caso de que se genere un error de división, asignamos un valor por defecto
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(text = "$label: $localValue/$maxValue", style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MinusButton(
                localValue= localValue,
                onValueChanged = onValueChanged,
            )

            // Barra de progreso
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp)
                    .background(Color.Gray.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp))
            ) {
                // Parte que muestra el progreso (verde)
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress)
                        .background(color, shape = RoundedCornerShape(8.dp))
                )
            }
            PlusButton(
                localValue= localValue,
                maxValue = maxValue,
                onValueChanged = onValueChanged,
            )
        }
    }

}
