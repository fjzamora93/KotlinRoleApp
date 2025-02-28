package com.unir.sheet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.ui.screens.MainScreen
import com.unir.sheet.ui.screens.character.CharacterCreatorScreen
import com.unir.sheet.ui.screens.character.CharacterListScreen
import com.unir.sheet.ui.screens.character.characterDetail.CharacterDetailScreen
import com.unir.sheet.ui.screens.character.items.CharacterInventoryScreen
import com.unir.sheet.ui.screens.character.items.ItemListScreen
import com.unir.sheet.ui.screens.character.skills.SkillListScreen
import com.unir.sheet.ui.screens.character.spells.CharacterSpellScreen
import com.unir.sheet.ui.screens.layout.FontsTemplateScreen
import com.unir.sheet.ui.screens.userscreens.LoginScreen
import com.unir.sheet.ui.screens.userscreens.UserProfileScreen
import com.unir.sheet.ui.viewmodels.CharacterViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Proveer instancias GLOBALES en el árbol de composables dentro de NavGraph (la navegación, el usuario, un carrito de la compra... lo que va a ser común)
    val navigationViewModel: NavigationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val characterViewModel: CharacterViewModel = hiltViewModel()

    CompositionLocalProvider(
        LocalNavigationViewModel provides navigationViewModel,
        LocalCharacterViewModel provides characterViewModel
    ) {
        // (LAUNCHEDEFECT) Llamamos a la función que va a detectar cualquier eventos de navegación
        HandleNavigationEvents(navController, navigationViewModel)

        // El NavHost define qué Screen se va a renderizar ante cada Ruta, dependiendo del LaunchedEffect de arriba
        NavHost(
            navController = navController,
            startDestination = ScreensRoutes.MainScreen.route
        ) {

            // Pantalla de inicio
            composable(ScreensRoutes.MainScreen.route) {
                MainScreen()
            }

            // Pantalla de creación del personaje
            composable(ScreensRoutes.CharacterCreatorScreen.route) {
                CharacterCreatorScreen()
            }

            composable(ScreensRoutes.CharacterListScreen.route) {
                CharacterListScreen()
            }

            composable(ScreensRoutes.ItemListScreen.route) {
                ItemListScreen()
            }

            composable(ScreensRoutes.FontTemplateScreen.route) {
                FontsTemplateScreen()
            }

            composable(ScreensRoutes.InventoryScreen.route) {
                CharacterInventoryScreen()
            }

            composable(ScreensRoutes.CharacterSpellScreen.route) {
                CharacterSpellScreen()
            }

            composable( ScreensRoutes.SkillListScreen.route ){
                SkillListScreen()
            }

            composable (ScreensRoutes.LoginScreen.route){
                LoginScreen()
            }

            composable (ScreensRoutes.UserProfileScreen.route){
                UserProfileScreen()
            }

            // Pantalla de detalle del personaje
            composable(
                ScreensRoutes.CharacterDetailScreen.route,
                arguments = listOf(navArgument("characterId") { type = NavType.IntType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                CharacterDetailScreen(characterId = characterId)
            }
        }
    }
}
