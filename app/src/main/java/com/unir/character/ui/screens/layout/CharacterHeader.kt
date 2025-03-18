package com.unir.character.ui.screens.layout


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.di.LocalCharacterViewModel
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.character.ui.screens.characterSheet.CharacterPortrait

@Composable
fun CharacterHeader(
    modifier: Modifier = Modifier,
    onClickMenu : () -> Unit
) {

    HeaderBody( onClickMenu = onClickMenu )
    HorizontalDivider(
        Modifier
            .background(Color(0xFFEEEEEE))
            .height(1.dp)
            .fillMaxWidth())

}

@Composable
fun HeaderBody(
    modifier: Modifier = Modifier,
    onClickMenu: () -> Unit = {},
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
) {
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()

    selectedCharacter?.let { character ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClickMenu() } ,
            horizontalArrangement = Arrangement.SpaceBetween, // Distribuye los elementos
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 📌 Imagen a la izquierda con clic
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable { onClickMenu() }
            ) {
                CharacterPortrait(character, onClick = { onClickMenu() })
            }

            // 📌 Información del personaje
            Column(
                modifier = Modifier.weight(1f) // Ocupa el espacio restante
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    text = "${character.race ?: ""} | ${character.rolClass ?: ""} | ${character.level ?: ""}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
