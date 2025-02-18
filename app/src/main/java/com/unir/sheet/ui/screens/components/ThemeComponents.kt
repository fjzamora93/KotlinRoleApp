package com.unir.sheet.ui.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MedievalDivider(
    color: Color = Color.DarkGray,
    thickness: Dp = 2.dp,
    endDecorationWidth: Dp = 16.dp,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Canvas(modifier = modifier.padding(vertical = 8.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Dibujar las decoraciones en los extremos
        drawRect(
            color = color,
            size = androidx.compose.ui.geometry.Size(endDecorationWidth.toPx(), thickness.toPx())
        )
        drawRect(
            color = color,
            topLeft = androidx.compose.ui.geometry.Offset(
                x = canvasWidth - endDecorationWidth.toPx(),
                y = 0f
            ),
            size = androidx.compose.ui.geometry.Size(endDecorationWidth.toPx(), thickness.toPx())
        )

        // Dibujar la línea central
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(
                x = endDecorationWidth.toPx(),
                y = thickness.toPx() / 2
            ),
            end = androidx.compose.ui.geometry.Offset(
                x = canvasWidth - endDecorationWidth.toPx(),
                y = thickness.toPx() / 2
            ),
            strokeWidth = thickness.toPx()
        )
    }
}
