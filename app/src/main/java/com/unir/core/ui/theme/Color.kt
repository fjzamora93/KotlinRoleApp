package com.unir.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object MedievalColours {
    // Colores de madera
    val WoodenLight = Color(0xFFD7B899)  // Tono claro de madera
    val WoodenRegular = Color(0xFF8B5A2B)  // Madera oscura cálida
    val WoodenDark = Color(0xFF5D3A00)  // Tono muy de madera


    // Colores de pergamino
    val ParchmentLight = Color(0xFFFFF8E1)  // Pergamino claro
    val ParchmentDark = Color(0xFFF5DEB3)  // Pergamino envejecido

    // Colores metálicos
    val Gold = Color(0xFFDAA520)  // Dorado brillante
    val Bronze = Color(0xFFCD7F32)  // Bronce antiguo
    val SteelGray = Color(0xFFB0C4DE)  // Gris acero
    val IronDark = Color(0xFF4B4B4B)  // Hierro oscuro

    // Colores de cuero
    val LeatherBrown = Color(0xFF654321)  // Marrón cuero
    val LeatherAged = Color(0xFF402A18)  // Cuero envejecido

    // Colores de naturaleza
    val ForestGreen = Color(0xFF228B22)  // Verde bosque
    val HerbGreen = Color(0xFF6B8E23)  // Verde hierba
    val StoneGray = Color(0xFF696969)  // Gris piedra
    val Turquoise = Color(0xFF2C8C8A)
    val NightSkyBlue = Color(0xFF191970)  // Azul cielo nocturno

    val greenGradient = Brush.horizontalGradient(
        colors = listOf(Bronze, HerbGreen)
    )

    val blueGradient = Brush.horizontalGradient(
        colors = listOf(StoneGray, Turquoise)
    )


    // Colores de fuego y luz
    val FlameOrange = Color(0xFFFF4500)  // Naranja llama
    val CandleYellow = Color(0xFFFFD700)  // Amarillo de vela

    // Colores adicionales temáticos
    val BloodRed = Color(0xFF8B0000)  // Rojo sangre
    val RoyalPurple = Color(0xFF6A0DAD)  // Púrpura real
    val AshGray = Color(0xFF708090)  // Gris ceniza
    val MidnightBlack = Color(0xFF1C1C1C)  // Negro medianoche

}



// SIN USO
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)