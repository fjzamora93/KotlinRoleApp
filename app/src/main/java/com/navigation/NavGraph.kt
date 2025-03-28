package com.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.di.LocalNavigationViewModel
import com.di.LocalAuthViewModel
import com.unir.adventure.ui.MainScreen
import com.unir.character.ui.screens.characterSheet.CharacterListScreen
import com.unir.character.ui.screens.characterSheet.CharacterDetailScreen
import com.unir.character.ui.screens.items.components.CharacterInventoryScreen
import com.unir.character.ui.screens.items.ItemListScreen
import com.unir.character.ui.screens.spells.CharacterSpellScreen
import com.ui.layout.FontsTemplateScreen
import com.unir.auth.ui.screens.LoginScreen
import com.unir.auth.ui.screens.UserProfileScreen
import com.unir.auth.viewmodels.AuthViewModel
import com.unir.character.ui.screens.characterform.CharacterEditorScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Proveer instancias GLOBALES en el árbol de composables dentro de NavGraph (la navegación, el usuario, un carrito de la compra... lo que va a ser común)
    val navigationViewModel: NavigationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    CompositionLocalProvider(
        LocalNavigationViewModel provides navigationViewModel,
        LocalAuthViewModel provides authViewModel,
    ) {
        // (LAUNCHEDEFECT) Llamamos a la función que va a detectar cualquier eventos de navegación
        HandleNavigationEvents(navController, navigationViewModel)

        // El NavHost define qué Screen se va a renderizar ante cada Ruta, dependiendo del LaunchedEffect de arriba
        NavHost(
            navController = navController,
            startDestination = ScreensRoutes.LoginScreen.route
        ) {

            // USUARIO Y FUNCIONALIDAD GENERAL
            composable(ScreensRoutes.MainScreen.route) {
                MainScreen()
            }
            composable (ScreensRoutes.LoginScreen.route){
                LoginScreen()
            }
            composable (ScreensRoutes.UserProfileScreen.route){
                UserProfileScreen()
            }



            // HECHIZOS, OBJETOS, INVENTARIO
            composable(ScreensRoutes.FontTemplateScreen.route) {
                FontsTemplateScreen()
            }
            composable(ScreensRoutes.ItemListScreen.route) {
                ItemListScreen()
            }
            composable(ScreensRoutes.InventoryScreen.route) {
                CharacterInventoryScreen()
            }
            composable(ScreensRoutes.CharacterSpellScreen.route) {
                CharacterSpellScreen()
            }



            //PERSONAJE
            composable(ScreensRoutes.CharacterListScreen.route) {
                CharacterListScreen()
            }
            composable(
                ScreensRoutes.CharacterDetailScreen.route,
                arguments = listOf(navArgument("characterId") { type = NavType.LongType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getLong("characterId") ?: 0
                CharacterDetailScreen(characterId = characterId)
            }
            composable(
                ScreensRoutes.CharacterEditorScreen.route,
                arguments = listOf(navArgument("characterId") { type = NavType.LongType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getLong("characterId") ?: 0
                CharacterEditorScreen(characterId = characterId)
            }


        }
    }
}
