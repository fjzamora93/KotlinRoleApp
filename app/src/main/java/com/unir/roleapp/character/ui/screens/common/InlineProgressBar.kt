package com.roleapp.character.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roleapp.core.ui.components.common.DefaultRow
import com.unir.roleapp.character.data.model.local.StatName

@Composable
fun InlineProgressBar(
    label: String = "",
    limit: Int = 0,
    minValue:Int = 0,
    maxValue: Int = 20,
    localValue: Int = 10,
    onValueChanged: (Int) -> Unit
) {
    val progress = if (maxValue > 0) localValue.toFloat() / maxValue.toFloat() else 1f

    DefaultRow {
        InlineStat(
            skillName = StatName.COMBAT,

            localValue = localValue,
            label = label,
            modifier = Modifier
                .weight(1f) // Permite que los otros elementos tengan espacio
                .wrapContentWidth(Alignment.Start)
        )

        // 🔹 Botón de restar (-)
        IconButton(onClick = {
            if (localValue > minValue) onValueChanged(localValue - 1)
            else onValueChanged(minValue)
        }) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = "Restar")
        }

        // 🔹 Barra de progreso con tamaño fijo
        Box(
            modifier = Modifier
                .width(100.dp)
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

        // 🔹 Botón de sumar (+)
        if (limit > 0 ){
            IconButton(onClick = {
                if (localValue < maxValue) onValueChanged(localValue + 1)
                else onValueChanged(maxValue)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Sumar")
            }
        }

    }
}
