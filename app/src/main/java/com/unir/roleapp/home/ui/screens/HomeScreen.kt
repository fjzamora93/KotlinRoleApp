package com.unir.roleapp.home.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.core.ui.components.sectioncard.SectionCard
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
            title = "Crear partida",
            description = "Crea y gestiona tus propias partidas de rol. Invita jugadores, personaliza reglas y vive aventuras únicas desde la app.",
            imageResName = "erudito",
            route = ScreensRoutes.AdventureMainScreen.route
        ),
        SectionCardItem(
            title = "Iniciar partida",
            description = "Inicia una nueva aventura, en solitario o con tu grupo. Elige tu historia, crea tu personaje y comienza la partida.",
            imageResName = "mazmorra",
            route = ScreensRoutes.AdventureListScreen.route
        ),
        SectionCardItem(
            title = "Personajes",
            description = "Crea y gestiona tus hojas de personaje. Personaliza atributos, habilidades y equipo para cada aventura. ¡Tu historia comienza aquí!",
            imageResName = "companeros",
            route = ScreensRoutes.CharacterListScreen.route
        ),
        SectionCardItem(
            title = "Usuarios",
            description = "Consulta y edita tu perfil de aventurero. Gestiona tus partidas, personajes y logros desde un solo lugar. ¡Tu historia te define!",
            imageResName = "cofre",
            route = ScreensRoutes.UserProfileScreen.route
        )
    )

    HorizontalPager(
        count = cardItems.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        contentPadding = PaddingValues(horizontal = 60.dp)
    ) { page ->
        val targetScale = if (page == pagerState.currentPage) 1f else 0.8f
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsHoveredAsState()
        val isHover by interactionSource.collectIsPressedAsState()
        val pressScale by animateFloatAsState(targetValue = if (isPressed || isHover) 0.95f else 1f)
        val finalScale = targetScale * pressScale

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = finalScale
                    scaleY = finalScale
            }
        ) {
            SectionCard(
                cardItems[page].title,
                cardItems[page].description,
                cardItems[page].imageResName,
                onClick = {
                    navController.navigate(cardItems[page].route)
                },
                interactionSource = interactionSource
            )
        }
    }
}