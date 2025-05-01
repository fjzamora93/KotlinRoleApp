package com.unir.roleapp.character.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RectangularButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.Add,
    text: String = "",
    isEnabled: Boolean = true,
) {
    // Obtener el color primario del tema
    val primaryColor = MaterialTheme.colorScheme.primary

    // Colores de fondo uniformes según el estado, pero siempre con el color primario
    val backgroundBrush = Brush.linearGradient(
        colors = listOf(primaryColor, primaryColor)
    )

    // Borde destacado cuando el botón está habilitado
    val borderColor = if (isEnabled) Color(0xFFDAA520) else Color.Transparent
    val iconColor = if (isEnabled) Color.White else Color.Gray
    val textColor = if (isEnabled) Color.White else Color.Gray

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundBrush)
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(enabled = true) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )

            if (text.isNotEmpty()) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text, // El texto siempre aparece, incluso cuando isEnabled es false
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
            }
        }
    }
}
