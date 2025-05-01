package com.unir.roleapp.core.ui.components.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unir.roleapp.R
@Composable
fun CrossSwordsAnimation() {
    val transition = rememberInfiniteTransition()
    val animationDuration = 400 // Duración total del ciclo (ida y vuelta)

    // Movimiento vertical (misma duración que el horizontal)
    val verticalOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Movimiento horizontal sincronizado
    val horizontalOffset by transition.animateFloat(
        initialValue = 0f,    // Separación inicial
        targetValue = -100f,   // Cruzan completamente al centro
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Rotación sincronizada con el movimiento
    val rotation by transition.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            // Espada izquierda
            Icon(
                painter = painterResource(R.drawable.sword),
                contentDescription = "Espada izquierda",
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .size(60.dp)
                    .graphicsLayer {
                        scaleX = -1f
                        translationY = verticalOffset
                        translationX = -horizontalOffset // Movimiento inverso
                        rotationZ = -rotation
                    }
            )

            // Espada derecha
            Icon(
                painter = painterResource(R.drawable.sword),
                contentDescription = "Espada derecha",
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                modifier = Modifier
                    .size(60.dp)
                    .graphicsLayer {
                        translationY = verticalOffset
                        translationX = horizontalOffset
                        rotationZ = rotation
                    }
            )
        }
    }
}