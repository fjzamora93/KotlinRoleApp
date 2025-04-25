package com.roleapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.adventure.ui.screens.AdventureMainScreen
import com.roleapp.character.ui.screens.characterSheet.CharacterListScreen
import com.roleapp.character.ui.screens.characterSheet.CharacterDetailScreen
import com.roleapp.character.ui.screens.items.components.CharacterInventoryScreen
import com.roleapp.character.ui.screens.items.ItemListScreen
import com.roleapp.character.ui.screens.spells.CharacterSpellScreen
import com.roleapp.core.ui.layout.FontsTemplateScreen
import com.roleapp.adventure.ui.screens.TemplateAdventureScreen
import com.roleapp.auth.ui.screens.LoginScreen
import com.roleapp.auth.ui.screens.UserProfileScreen
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.character.ui.screens.characterform.CharacterEditorScreen
<<<<<<< Updated upstream
import com.unir.roleapp.adventure.ai.screens.SceneTestScreen
=======
import com.roleapp.core.di.LocalLanguageSetter
import com.unir.roleapp.adventure.ui.screens.AdventureContextScreen
import com.unir.roleapp.adventure.ui.screens.CreateAdventureScreen
import com.unir.roleapp.adventure.ui.viewmodels.AdventureContextViewModel
import com.unir.roleapp.adventure.ui.viewmodels.CreateAdventureViewModel
import com.unir.roleapp.core.navigation.LocalizedApp
import com.unir.roleapp.home.ui.screens.HomeScreen
>>>>>>> Stashed changes


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

<<<<<<< Updated upstream
            // USUARIO Y FUNCIONALIDAD GENERAL
            composable(ScreensRoutes.AdventureMainScreen.route) {
                AdventureMainScreen()
=======
            // El NavHost define qué Screen se va a renderizar ante cada Ruta, dependiendo del LaunchedEffect de arriba
            NavHost(
                navController = navController,
                startDestination = ScreensRoutes.LoginScreen.route
            ) {

                // USUARIO Y FUNCIONALIDAD GENERAL
                composable(ScreensRoutes.AdventureListScreen.route) {
                    TemplateAdventureScreen()
                }
                composable(ScreensRoutes.LoginScreen.route) {
                    LoginScreen()
                }
                composable(ScreensRoutes.UserProfileScreen.route) {
                    UserProfileScreen(
                        selectedLang = selectedLang,
                        onLanguageSelected = { selectedLang = it }
                    )
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


                // ADVENTURE

                // 1) Pantalla principal
                composable(ScreensRoutes.AdventureMainScreen.route) {
                    AdventureMainScreen(navController)
                }

                // 2) Pantalla intermedia de creación
                composable(ScreensRoutes.CreateAdventureScreen.route) {
                    CreateAdventureScreen(onNext = { id ->
                        navController.navigate(
                            ScreensRoutes.AdventureContextScreen.createRoute(id)
                        )
                    })
                }

                // 3) Pantalla final con argumento
                composable(
                    route = ScreensRoutes.AdventureContextScreen.route,
                    arguments = listOf(navArgument("adventureId") {
                        type = NavType.StringType
                    })
                ) { backStack ->
                    val adventureId = backStack.arguments!!.getString("adventureId")!!
                    AdventureContextScreen(
                        adventureId = adventureId,
                        onCancel = { navController.popBackStack() },
                        onFinish = { navController.popBackStack() }
                    )
                }

                // HOME
                composable(ScreensRoutes.HomeScreen.route) {
                    HomeScreen(navController = navController)
                }
>>>>>>> Stashed changes
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


            // ADVENTURE
            composable(ScreensRoutes.TemplateAdventureScreen.route) {
                TemplateAdventureScreen()
            }

            // TEST
            composable(ScreensRoutes.AiTestScreen.route){
                SceneTestScreen()
            }


        }
    }
}
