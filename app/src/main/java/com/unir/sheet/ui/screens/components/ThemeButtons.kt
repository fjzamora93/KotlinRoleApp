package com.unir.sheet.ui.screens.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import com.unir.sheet.util.MedievalColours


@Composable
fun PlusButton(
    localValue: Int,
    onValueChanged: (Int) -> Unit,
    maxValue: Int = 9999,
    style: String = "Rounded"
) {
    var modifier = when (style) {
        "Rounded" -> medievalButtonStyleRounded()
        "Square" -> medievalButtonStyleSquare()
        else -> medievalButtonStyleRounded()
    }

    IconButton(
        onClick = {
            if (localValue < maxValue) {
                onValueChanged(localValue + 1)
            }
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Increment",
            modifier = Modifier
                .size(24.dp) // Tamaño del ícono
                .shadow(4.dp, CircleShape), // Sombra para dar relieve
            tint = MedievalColours.Gold
        )
    }
}



@Composable
fun MinusButton(
    localValue: Int,
    onValueChanged: (Int) -> Unit,
    minValue: Int = 0,
    style: String = "Rounded"
){
    var modifier = when (style) {
        "Rounded" -> medievalButtonStyleRounded()
        "Square" -> medievalButtonStyleSquare()
        else -> medievalButtonStyleRounded()
    }
    IconButton(
        modifier = modifier,
        onClick = {
            if (localValue > minValue) {
                onValueChanged(localValue - 1)
            }
        }) {
        Icon(
            imageVector = Icons.Default.Remove,
            contentDescription = "Decrement",
            modifier = Modifier
                .size(24.dp) // Tamaño del ícono
                .shadow(4.dp, CircleShape), // Sombra para dar relieve
            tint = MedievalColours.Gold
        )
    }
}



@Composable
fun AddButton(
    onClick : () -> Unit,
){
    IconButton(
        onClick = onClick
        ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            modifier = Modifier
                .size(24.dp) // Tamaño del ícono
                .shadow(4.dp, CircleShape), // Sombra para dar relieve
            tint = MedievalColours.IronDark
        )
    }
}

@Composable
fun MenuMedievalButton(
    onClick: () -> Unit,
    modifier: Modifier = medievalButtonStyleSquare(size = 90.dp),
    icon: ImageVector = Icons.Default.Add,
    text: String = ""
){
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ){
        IconButton(
            modifier = modifier,
            onClick = { onClick() }
        ) {
            Icon(imageVector = icon, contentDescription = "", tint = MedievalColours.Gold, modifier = Modifier.size(50.dp))
        }
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}


@Composable
fun NavigationButton(
    onClick: () -> Unit,
    modifier: Modifier = medievalButtonStyleSquare(size = 60.dp),
    icon: ImageVector = Icons.Default.Add,
    text: String = ""
){
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ){
        Text(text = text, style = MaterialTheme.typography.titleSmall)
        IconButton(
            modifier = modifier,
            onClick = { onClick() }
        ) {
            Icon(imageVector = icon, contentDescription = "", tint = MedievalColours.Gold, modifier = Modifier.size(40.dp))
        }
    }
}



fun medievalButtonStyleSquare(
    size: Dp = 40.dp,
    backgroundColor1: Color = MedievalColours.WoodenRegular,
    backgroundColor2: Color = MedievalColours.WoodenDark,
    borderColor: Color = Color(0xFFDAA520)
): Modifier {
    return Modifier
        .size(size) // Ajustar el tamaño
        .clip(RoundedCornerShape(8.dp)) // Bordes ligeramente redondeados
        .background(Brush.linearGradient(colors = listOf(backgroundColor1, backgroundColor2)))
        .border(2.dp, borderColor, RoundedCornerShape(8.dp)) // Borde dorado siguiendo la forma
        .padding(8.dp)
}


fun medievalButtonStyleRounded(): Modifier {
    return Modifier
        .size(40.dp) // Ajustar el tamaño
        .clip(CircleShape) // Forma circular para simular un escudo o medallón
        .background(Brush.linearGradient(colors = listOf(MedievalColours.WoodenRegular, MedievalColours.WoodenDark)))
        .border(2.dp, Color(0xFFDAA520), CircleShape)
        .padding(8.dp)
}


