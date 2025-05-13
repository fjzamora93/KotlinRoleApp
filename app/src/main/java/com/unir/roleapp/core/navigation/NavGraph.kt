package com.roleapp.core.navigation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.character.ui.screens.characterSheet.CharacterListScreen
import com.roleapp.character.ui.screens.characterSheet.CharacterDetailScreen
import com.roleapp.core.ui.layout.FontsTemplateScreen
import com.roleapp.auth.ui.screens.LoginScreen
import com.roleapp.auth.ui.screens.UserProfileScreen
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.character.ui.screens.characterform.CharacterEditorScreen
import com.roleapp.core.di.LocalLanguageSetter
import com.unir.roleapp.MyApplication.Companion.context
import com.unir.roleapp.adventure.ui.screens.HomeAdventureScreen
import com.unir.roleapp.core.navigation.LocalizedApp
import com.unir.roleapp.home.ui.screens.HomeScreen
import com.unir.roleapp.adventure.ui.screens.AdventureContextScreen
import com.unir.roleapp.adventure.ui.screens.AdventureMainScreen
import com.unir.roleapp.adventure.ui.screens.CreateAdventureScreen
import com.unir.roleapp.adventure.ui.screens.TemplateAdventureScreen
import com.unir.roleapp.adventure.ui.screens.WaitingRoomScreen
import com.unir.roleapp.adventure.ui.viewmodels.WaitingRoomViewModel
import com.unir.roleapp.adventure.ui.screens.form.*
import com.unir.roleapp.adventure.ui.viewmodels.AdventureFormViewModel

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
                    AdventureMainScreen()
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


                // ---- AVENTURA ----

                // 1) Pantalla de creación inicial
                composable(ScreensRoutes.AdventureMainScreen.route) {
                    AdventureMainScreen()
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
                        onCopyCode  = {
                            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Código de partida", adventureId)
                            clipboardManager.setPrimaryClip(clip)
                            Toast.makeText(context, "Código copiado al portapapeles", Toast.LENGTH_SHORT).show()
                        },
                        onAddPlayer = { },
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


                // Creación aventura Esteban

                composable(ScreensRoutes.AdventureHomeScreen.route) {
                    HomeAdventureScreen(navController)
                }

                composable(
                    route = ScreensRoutes.TitleScreen.route
                ) { backStackEntry ->
                    val formEntry = backStackEntry
                    val formVm: AdventureFormViewModel = hiltViewModel(formEntry)

                    TitleScreen(
                        viewModel = formVm,
                        onNext = {
                            navController.navigate(ScreensRoutes.HistoricalContextScreen.route)
                        }
                    )
                }


                composable(
                    route = ScreensRoutes.HistoricalContextScreen.route
                ) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(ScreensRoutes.TitleScreen.route)
                    }
                    val formVm: AdventureFormViewModel = hiltViewModel(parentEntry)

                    HistoricalContextScreen(
                        viewModel = formVm,
                        onNext = {
                            navController.navigate(ScreensRoutes.ActsScreen.route)
                        }
                    )
                }

                composable(
                    route = ScreensRoutes.ActsScreen.route
                ) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(ScreensRoutes.TitleScreen.route)
                    }
                    val formVm: AdventureFormViewModel = hiltViewModel(parentEntry)

                    ActsScreen(
                        viewModel = formVm,
                        navController = navController,
                    )
                }

                composable(ScreensRoutes.MyAdventuresScreen.route) {
                    MyAdventuresScreen(
                        onAdventureClick = { adventureId ->
                            // aquí decides a dónde ir, p.ej. la sala de espera:
                            navController.navigate(ScreensRoutes.WaitingRoomScreen.createRoute(adventureId))
                        },
                        onCreateNew = {
                            // y aquí a la pantalla de creación:
                            navController.navigate(ScreensRoutes.AdventureMainScreen.route)
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

