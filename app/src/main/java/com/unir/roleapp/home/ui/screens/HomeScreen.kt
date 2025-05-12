package com.unir.roleapp.home.ui.screens

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
fun HomeScreen(navController: NavHostController) {
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
            title = "Partidas",
            description = "Entra a la sección de partidas para decidir que vas a hacer. ¿iniciar una nueva partida o buscar una existente? ¿crear una nueva partida?",
            imageResName = "partidas_cartas",
            route = ScreensRoutes.TitleScreen.route
        ),
        SectionCardItem(
            title = "Personajes",
            description = "Crea y gestiona tus hojas de personaje. Personaliza atributos, habilidades y equipo para cada aventura. ¡Tu historia comienza aquí!",
            imageResName = "companeros",
            route = ScreensRoutes.CharacterListScreen.route
        ),
        SectionCardItem(
            title = "Usuarios",
            description = "Busca y contacta con tus amigos para crear nuevas aventuras o remotar alguna aventura sin acabar.",
            imageResName = "taberna_aventureros",
            route = ScreensRoutes.UserProfileScreen.route
        )
    )

    CardCarousel(cardItems, pagerState, navController)
}