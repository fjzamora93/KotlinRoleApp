package com.unir.character.ui.screens.character.haracterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.unir.character.data.model.local.CharacterEntity


@Composable
fun StatSection(
    editableCharacter: CharacterEntity,
    onCharacterChange: (CharacterEntity) -> Unit
) {


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


@Composable
fun HitPointsBar(
    label: String = "",
    maxValue: Int = 20,
    localValue: Int = 10,
    onValueChanged: (Int) -> Unit
) {
    // Calcular el progreso asegurando que maxValue no sea 0
    val progress = if (maxValue > 0) localValue.toFloat() / maxValue.toFloat() else 1f

    // Contenedor principal
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fila con botones y texto centrado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón de restar (-)
            IconButton(
                onClick = { if (localValue > 0) onValueChanged(localValue - 1) }
            ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Restar")
            }

            // Texto central (Label y valores)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "$localValue / $maxValue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Botón de sumar (+)
            IconButton(
                onClick = { if (localValue < maxValue) onValueChanged(localValue + 1) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Sumar")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Barra azul del indicador
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.3f)) // Fondo gris claro
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(MaterialTheme.colorScheme.primary) // Azul según el tema
            )
        }
    }
}
