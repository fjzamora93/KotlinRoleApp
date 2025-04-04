package com.unir.roleapp.home.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.R
import com.unir.roleapp.core.ui.components.SectionCard


@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainLayout(){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center
            ){
                HomeScreenBody()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreenBody() {
    val pagerState = rememberPagerState()
    val titles = listOf("Crear partida", "Iniciar partida", "Personajes")
    val descriptions = listOf(
        "Crea y gestiona tus propias partidas de rol. Invita jugadores, personaliza reglas y vive aventuras únicas desde la app",
        "Inicia una nueva aventura, en solitario o con tu grupo. Elige tu historia, crea tu personaje y comienza la partida.",
        "Crea y gestiona tus hojas de personaje. Personaliza atributos, habilidades y equipo para cada aventura."
    )
    val images = listOf(
        painterResource(id = R.drawable.erudito),
        painterResource(id = R.drawable.mazmorra),
        painterResource(id = R.drawable.companeros)
    )
    val routes = listOf(
        ScreensRoutes.AdventureMainScreen.route,
        ScreensRoutes.AdventureListScreen.route,
        ScreensRoutes.CharacterListScreen.route
    )

    HorizontalPager(
        count = titles.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(horizontal = 32.dp),
        itemSpacing = 16.dp
    ) { page ->
        SectionCard(
            titles[page],
            descriptions[page],
            images[page],
            onClick = {
                routes[page]
            }
        )
    }
}