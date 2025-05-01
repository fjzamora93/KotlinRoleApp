package com.unir.roleapp.core.navigation

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.unir.roleapp.adventure.ui.screens.AdventureContextScreen
import com.unir.roleapp.adventure.ui.screens.AdventureMainScreen
import com.unir.roleapp.adventure.ui.screens.CreateAdventureScreen
import com.unir.roleapp.adventure.ui.screens.WaitingRoomScreen
import com.unir.roleapp.adventure.ui.viewmodels.WaitingRoomViewModel
import com.unir.roleapp.auth.ui.screens.LoginScreen
import com.unir.roleapp.auth.ui.screens.UserProfileScreen
import com.unir.roleapp.auth.viewmodels.AuthViewModel
import com.unir.roleapp.character.ui.screens.characterSheet.CharacterListScreen
import com.unir.roleapp.character.ui.screens.characterSheet.CharacterDetailScreen
import com.unir.roleapp.character.ui.screens.characterform.CharacterEditorScreen
import com.unir.roleapp.core.di.LocalAuthViewModel
import com.unir.roleapp.core.di.LocalLanguageSetter
import com.unir.roleapp.core.di.LocalNavigationViewModel
import com.unir.roleapp.home.ui.screens.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    // ViewModels compartidos
    val navigationViewModel: NavigationViewModel = viewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

    // Idioma seleccionado
    var selectedLang by rememberSaveable { mutableStateOf("es") }

    CompositionLocalProvider(
        LocalNavigationViewModel provides navigationViewModel,
        LocalAuthViewModel      provides authViewModel,
        LocalLanguageSetter     provides { lang -> selectedLang = lang }
    ) {
        LocalizedApp(language = selectedLang) {
            HandleNavigationEvents(navController, navigationViewModel)

            NavHost(
                navController    = navController,
                startDestination = ScreensRoutes.LoginScreen.route
            ) {
                // ---- LOGIN / USUARIO ----
                composable(ScreensRoutes.LoginScreen.route) {
                    LoginScreen()
                }
                composable(ScreensRoutes.UserProfileScreen.route) {
                    UserProfileScreen(
                        selectedLang      = selectedLang,
                        onLanguageSelected = { selectedLang = it }
                    )
                }

                // ---- PERSONAJES ----
                composable(ScreensRoutes.CharacterListScreen.route) {
                    CharacterListScreen()
                }
                composable(
                    ScreensRoutes.CharacterDetailScreen.route,
                    arguments = listOf(navArgument("characterId") {
                        type = NavType.LongType
                    })
                ) { backStack ->
                    val characterId = backStack.arguments!!.getLong("characterId")
                    CharacterDetailScreen(characterId = characterId)
                }
                composable(
                    ScreensRoutes.CharacterEditorScreen.route,
                    arguments = listOf(navArgument("characterId") {
                        type = NavType.LongType
                    })
                ) { backStack ->
                    val characterId = backStack.arguments!!.getLong("characterId")
                    CharacterEditorScreen(characterId = characterId)
                }

                // ---- AVENTURA ----

                // 1) Pantalla de creación inicial
                composable(ScreensRoutes.AdventureMainScreen.route) {
                    AdventureMainScreen(navController = navController)
                }

                // 2) Sala de espera
                composable(
                    route = ScreensRoutes.WaitingRoomScreen.route,
                    arguments = listOf(navArgument("adventureId") {
                        type = NavType.StringType
                    })
                ) { backStack ->
                    val adventureId = backStack.arguments!!.getString("adventureId")!!

                    val vm: WaitingRoomViewModel = hiltViewModel()
                    LaunchedEffect(adventureId) { vm.loadCharacters(adventureId) }

                    val characters by vm.characters.collectAsState(initial = emptyList())
                    val loading    by vm.loading.collectAsState(initial = false)
                    val error      by vm.error.collectAsState(initial = null)

                    WaitingRoomScreen(
                        adventureId = adventureId,
                        characters  = characters,
                        loading     = loading,
                        error       = error,
                        onContinue  = {
                            navController.navigate(
                                ScreensRoutes.CreateAdventureScreen.createRoute(adventureId)
                            )
                        }
                    )
                }

                // 3) Ajustes finales antes del contexto
                composable(
                    route     = ScreensRoutes.CreateAdventureScreen.route,
                    arguments = listOf(navArgument("adventureId") {
                        type = NavType.StringType
                    })
                ) { backStack ->
                    val adventureId = backStack.arguments!!.getString("adventureId")!!
                    CreateAdventureScreen(
                        navController = navController,
                        onNext        = { id ->
                            navController.navigate(
                                ScreensRoutes.AdventureContextScreen.createRoute(id)
                            )
                        }
                    )
                }

                // 4) Contexto histórico y de personajes
                composable(
                    route     = ScreensRoutes.AdventureContextScreen.route,
                    arguments = listOf(navArgument("adventureId") {
                        type = NavType.StringType
                    })
                ) { backStack ->
                    val adventureId = backStack.arguments!!.getString("adventureId")!!
                    AdventureContextScreen(
                        adventureId = adventureId,
                        onCancel    = { navController.popBackStack() },
                        onFinish    = { navController.popBackStack() }
                    )
                }

                // ---- HOME ----
                composable(ScreensRoutes.HomeScreen.route) {
                    HomeScreen(navController = navController)
                }
            }
        }
    }
}
