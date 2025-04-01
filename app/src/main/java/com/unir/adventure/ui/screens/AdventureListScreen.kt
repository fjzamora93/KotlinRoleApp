package com.unir.adventure.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.core.ui.components.common.DefaultRow
import com.unir.core.ui.layout.MainLayout
import com.unir.adventure.ui.viewmodels.SceneViewModel
import com.unir.roleapp.R


// TODO: NO HAY POR QUÉ USAR EL MAIN LAYOUT (que lo que tiene dentro es un Scaffold). Como alternativa, podemos crear un nuevo Scaffold.
@Composable
fun TemplateAdventureScreen() {
    LaunchedEffect(Unit) {
        Log.d("CharacterScreen", "NavController está listo")
    }

    MainLayout {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1F1D36),
                            Color(0xFF3F3351)
                        )
                    )
                )
        ) {
            TemplateAdventureScreenBody(Modifier.fillMaxSize())
        }
    }
}

@Composable
fun TemplateAdventureScreenBody(
    modifier: Modifier = Modifier,
    sceneViewModel: SceneViewModel = hiltViewModel(),

    ) {
    val scenes by sceneViewModel.scenes.collectAsState()


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo o imagen decorativa
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.d20_dice),
            contentDescription = "Logo de RolApp",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))


        scenes.forEach { currentScene ->
            DefaultRow() {
                Text(
                    text = currentScene.name ,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFD4D4D8)),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = currentScene.description,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFD4D4D8)),
                    textAlign = TextAlign.Center
                )
            }


        }



        Spacer(modifier = Modifier.height(24.dp))

        // IMagen... por poner algo bonito
        Image(
            painter = painterResource(id = R.drawable.taberna),
            contentDescription = "Paisaje de fantasía",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

    }
}

