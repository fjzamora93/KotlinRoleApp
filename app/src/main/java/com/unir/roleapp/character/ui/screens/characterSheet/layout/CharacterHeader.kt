package com.roleapp.character.ui.screens.common.layout


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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.screens.characterSheet.components.CharacterPortrait
import com.roleapp.character.ui.screens.characterform.components.PortraitGridComponent
import com.roleapp.character.ui.screens.common.dialogues.CharacterDialog
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.core.ui.theme.ThemeViewModel

@Composable
fun CharacterHeader(
    modifier: Modifier = Modifier,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    onClickMenu : () -> Unit,
    onClickAdventure: () -> Unit,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
) {
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    var isEditingPortrait by remember { mutableStateOf(false) }

    val currentRoute by navigationViewModel.currentRoute.collectAsState()

    selectedCharacter?.let { character ->
        Row(
            modifier = modifier.fillMaxWidth().background( CustomColors.BlackGradient)
                .clickable {  navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(
                    selectedCharacter!!.id)) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 📌 Imagen a la izquierda con clic
            Box(
                modifier = Modifier.size(120.dp)

            ) {
                CharacterPortrait(
                    character,
                    onClick = {  onClickMenu() }
                )
            }

            // 📌 Información del personaje
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )

                Text(
                    text = "${character.race ?: ""} | ${character.rolClass ?: ""} | ${character._level ?: ""}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            }

            // 📌 Descansar... o hacer cualquier función (consultar hechizos, inventarios, sesión, etc)
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .clickable { onClickMenu() },
                horizontalAlignment = Alignment.CenterHorizontally // para centrar ícono y texto
            ) {

                IconButton(
                    onClick = { onClickAdventure() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Flag,
                        contentDescription = "Más opciones",
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.size(60.dp)

                    )
                }
                Text(
                    text = "Aventura",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }

        HorizontalDivider(
            Modifier
                .background(Color(0xFFEEEEEE))
                .height(1.dp)
                .fillMaxWidth()
        )


        if (isEditingPortrait) {
            Dialog(onDismissRequest = { isEditingPortrait = false }) {
                PortraitGridComponent(
                    onPortraitSelected = { portraitString ->
                        character.imgUrl = portraitString
                        isEditingPortrait = false
                        characterViewModel.saveCharacter(character)
                    },
                    onBackPressed = { isEditingPortrait = false }
                )

            }
        }
    }
}
