package com.roleapp.core.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.character.ui.screens.characterSheet.CharacterListScreen
import com.roleapp.character.ui.screens.characterSheet.CharacterDetailScreen
import com.roleapp.auth.ui.screens.LoginScreen
import com.roleapp.auth.ui.screens.UserProfileScreen
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.character.ui.screens.characterform.CharacterEditorScreen
import com.roleapp.core.di.LocalLanguageSetter
import com.unir.roleapp.adventure.ui.screens.HomeAdventureScreen
import com.unir.roleapp.core.navigation.LocalizedApp
import com.unir.roleapp.home.ui.screens.HomeScreen
import com.unir.roleapp.adventure.ui.screens.TemplateAdventureScreen
import com.unir.roleapp.adventure.ui.screens.form.*
import com.unir.roleapp.core.navigation.helpers.AdventureFormGraph
import com.unir.roleapp.core.navigation.helpers.SharedAdventureForm

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Proveer instancias GLOBALES en el árbol de composables dentro de NavGraph (la navegación, el usuario, un carrito de la compra... lo que va a ser común)
    val navigationViewModel: NavigationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

    // Gestuñib de uduinas
    var selectedLang by rememberSaveable { mutableStateOf("es") }

    CompositionLocalProvider(
        LocalNavigationViewModel provides navigationViewModel,
        LocalAuthViewModel provides authViewModel,
        LocalLanguageSetter provides { lang -> selectedLang = lang }
    ) {
        LocalizedApp(
            language = selectedLang,
        ) {
            // (LAUNCHEDEFECT) Llamamos a la función que va a detectar cualquier eventos de navegación
            HandleNavigationEvents(navController, navigationViewModel)

            // El NavHost define qué Screen se va a renderizar ante cada Ruta, dependiendo del LaunchedEffect de arriba
            NavHost(
                navController = navController,
                startDestination = ScreensRoutes.LoginScreen.route
            ) {

                // USUARIO Y FUNCIONALIDAD GENERAL
                composable(ScreensRoutes.AdventureMainScreen.route) {
//                    AdventureMainScreen()
                }
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


                // —————————— ADVENTURE FORM SUB‐GRAPH ——————————
                composable(ScreensRoutes.AdventureHomeScreen.route) {
                    HomeAdventureScreen(navController)
                }

                navigation(
                    route = AdventureFormGraph.pattern,
                    startDestination = ScreensRoutes.TitleScreen.route,
                    arguments = listOf(
                        navArgument("adventureId") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = ""
                        }
                    )
                ) {
                    // TitleScreen
                    composable(ScreensRoutes.TitleScreen.route) {
                        SharedAdventureForm(navController) { formVm, advId ->
                            TitleScreen(
                                viewModel   = formVm,
                                adventureId = advId.toString(),
                                onNext      = {
                                    navController.navigate(ScreensRoutes.HistoricalContextScreen.route)
                                }
                            )
                        }
                    }

                    // HistoricalContextScreen
                    composable(ScreensRoutes.HistoricalContextScreen.route) {
                        SharedAdventureForm(navController) { formVm, /* advId no se usa aquí */ _ ->
                            HistoricalContextScreen(
                                viewModel = formVm,
                                onNext    = {
                                    navController.navigate(ScreensRoutes.ActsScreen.route)
                                }
                            )
                        }
                    }

                    // ActsScreen
                    composable(ScreensRoutes.ActsScreen.route) {
                        SharedAdventureForm(navController) { formVm, _ ->
                            ActsScreen(
                                viewModel     = formVm,
                                navController = navController
                            )
                        }
                    }
                }

                composable(ScreensRoutes.MyAdventuresScreen.route) {
                    MyAdventuresScreen(
                        onAdventureClick = { adventureId ->
                            navController.navigate(AdventureFormGraph.createRoute(adventureId)) {
                                launchSingleTop = true
                            }
                        },
                        onCreateNew = {
                            navController.navigate(ScreensRoutes.TitleScreen.route)
                        }
                    )
                }

                // HOME
                composable(ScreensRoutes.HomeScreen.route) {
                    HomeScreen(navController = navController)
                }
            }
        }
    }
}

