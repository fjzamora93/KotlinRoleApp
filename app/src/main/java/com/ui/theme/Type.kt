package com.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.unir.sheet.R

// Set of Material typography styles to start with
val CustomType = Typography(

    /** Título POR DEFECTO, sin nada especial */
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = MedievalColours.LeatherAged,
        textAlign = TextAlign.Center
    ),

    /** Título MUY Medieval - Poco legible */
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.metamorphus)),
        fontWeight = FontWeight.Bold, // Peso más grueso para resaltar
        fontSize = 22.sp, // Un poco más grande que bodyMedium
        lineHeight = 25.sp, // Altura de línea proporcionalmente mayor
        letterSpacing = 0.15.sp,
        color = MedievalColours.LeatherAged
    ),

    // TÍTULO PEQUEÑO MEDIEVAL - para destacar un texto
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.cincel)),
        fontWeight = FontWeight.SemiBold, // Peso ligeramente más grueso para destacar
        fontSize = 16.sp, // Tamaño mediano para lectura cómoda
        lineHeight = 26.sp, // Altura de línea más espaciosa para claridad
        letterSpacing = 0.25.sp, // Separación ligera entre letras
        color = MedievalColours.LeatherAged
    ),

    // Body STANDAR - sin tipografías extrañas y sin colores extraño
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),

    // TEXTO DE CUERPO MEDIEVAL - más legible que el titleMedium
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.antiqua)),
        fontWeight = FontWeight.SemiBold, // Peso ligeramente más grueso para destacar
        fontSize = 18.sp, // Tamaño mediano para lectura cómoda
        lineHeight = 26.sp, // Altura de línea más espaciosa para claridad
        letterSpacing = 0.25.sp, // Separación ligera entre letras
        color = MedievalColours.LeatherAged
    ),

    // Letra METAMORPHUS MUY Medieval- apta para Números y cifras (poco legible)
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.medievalsharp)),
        fontWeight = FontWeight.SemiBold, // Peso ligeramente más grueso para destacar
        fontSize = 16.sp, // Tamaño mediano para lectura cómoda
        lineHeight = 26.sp, // Altura de línea más espaciosa para claridad
        letterSpacing = 0.25.sp, // Separación ligera entre letras
        color = MedievalColours.LeatherAged
    ),

    // Footers y textos de menor importancia. NO tiene ningún estilo
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),






    )