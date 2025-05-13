package com.unir.roleapp.adventure.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.core.ui.components.common.CardCarousel
import com.unir.roleapp.core.ui.components.sectioncard.SectionCardItem


@Composable
fun HomeAdventureScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainLayout(){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ){
                HomeScreenBody(navController)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenBody(navController: NavHostController) {
    val pagerState = rememberPagerState()

    val cardItems = listOf(
        SectionCardItem(
            title = "Crear Aventura",
            description = "Crea y gestiona tus propias partidas de rol. Invita jugadores, personaliza reglas y vive aventuras únicas desde la app.",
            imageResName = "erudito2",
            route = ScreensRoutes.TitleScreen.route
        ),

        SectionCardItem(
            title = "Listado de Aventuras",
            description = "Listado de todas tus Aventuras. Reanuda la Aventura que hayas dejado a medias con tus compañeros.",
            imageResName = "adventure_init",
            route = ScreensRoutes.MyAdventuresScreen.route
        )
        /*SectionCardItem(
            title = "Iniciar partida",
            description = "Inicia una nueva aventura, en solitario o con tu grupo. Elige tu historia, crea tu personaje y comienza la partida.",
            imageResName = "adventure_init",
            route = ScreensRoutes.AdventureListScreen.route
        ),
        SectionCardItem(
            title = "Reanudar partida",
            description = "Reanuda la partida que hayas dejado a medias con tus compañeros.",
            imageResName = "morning_camp",
            route = ScreensRoutes.CharacterListScreen.route
        )*/
    )

    CardCarousel(cardItems, pagerState, navController)
}