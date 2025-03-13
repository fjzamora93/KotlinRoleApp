package com.unir.character.ui.screens.character.characterDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Brush
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes
import com.unir.character.ui.screens.components.MenuMedievalButton


@Composable
fun CharacterMenu(
    navigation: NavigationViewModel = LocalNavigationViewModel.current,
){

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ){
        // BOTÓN INVENTARIO
        MenuMedievalButton(
            icon = Icons.Default.Backpack,
            text = "Inventario",
            onClick = {
                navigation.navigate(ScreensRoutes.InventoryScreen.route)
            }
        )

        // HECHIZOS
        MenuMedievalButton(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            text = "Hechizos",
            onClick = {
                navigation.navigate(ScreensRoutes.CharacterSpellScreen.route)
            }
        )

        // HABILIDADES
        MenuMedievalButton(
            icon = Icons.Default.Brush,
            text = "Habilidades",
            onClick = {
                navigation.navigate(ScreensRoutes.SkillListScreen.route)
            }
        )




    }
}


