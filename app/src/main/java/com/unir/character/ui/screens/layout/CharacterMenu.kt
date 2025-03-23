package com.unir.character.ui.screens.layout


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.di.LocalNavigationViewModel
import com.di.LocalAuthViewModel
import com.di.LocalCharacterViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes
import com.ui.layout.MenuOption
import com.unir.auth.viewmodels.AuthViewModel
import com.unir.character.viewmodels.CharacterViewModel


@Composable
fun CharacterMenu(
    drawerState: DrawerState,
    onClose: () -> Unit,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    content: @Composable () -> Unit,
) {

    val character by characterViewModel.selectedCharacter.collectAsState()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column {

                    MenuOption(
                        text = "Inventario",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.InventoryScreen.route) },
                        icon = Icons.Default.Backpack,
                    )

                    MenuOption(
                        text = "Hechizos disponibles",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterSpellScreen.route) },
                        icon = Icons.AutoMirrored.Filled.MenuBook
                    )


                    MenuOption(
                        text = "Editar personaje",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterEditorScreen.createRoute(
                            character?.id ?: 0)) },
                        icon = Icons.Default.EditOff
                    )


                    MenuOption(
                        text = "Conectarse a mesa de juego",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterListScreen.route) },
                        icon = Icons.Default.GpsFixed
                    )


                    MenuOption(
                        text = "Tipografías y fuentes",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.FontTemplateScreen.route) },
                        icon = Icons.Default.FontDownload
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onClose) {
                        Text("Cerrar")
                    }


                }
            }
        },
        content = content
    )
}

