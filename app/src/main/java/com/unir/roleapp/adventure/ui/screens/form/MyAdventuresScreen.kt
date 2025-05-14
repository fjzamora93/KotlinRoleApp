package com.unir.roleapp.adventure.ui.screens.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

import com.roleapp.core.ui.layout.MainLayout
import com.unir.roleapp.adventure.ui.viewmodels.MyAdventuresViewModel
import com.unir.roleapp.core.navigation.helpers.AdventureFormGraph
import com.unir.roleapp.core.ui.components.common.CardCarousel
import com.unir.roleapp.core.ui.components.sectioncard.SectionCardItem

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyAdventuresScreen(
    navController: NavHostController,
    onAdventureClick: (String) -> Unit, // ya no te hace falta si usas route en el carousel
    onCreateNew: () -> Unit,
    vm: MyAdventuresViewModel = hiltViewModel()
) {
    // 1) Estado de la UI
    val adventures by vm.adventures.collectAsState()
    val loading    by vm.loading.collectAsState()
    val error      by vm.error.collectAsState()

    // 2) Única invocación de MainLayout (sin navController ni topBar)
    MainLayout {
        // tu contenido va aquí: SmallTopAppBar + carrusel
        Column(Modifier.fillMaxSize()) {
            // — Opcional: tu propia AppBar dentro del content
            SmallTopAppBar(
                title = { Text("Mis Aventuras") },
                actions = {
                    TextButton(onClick = onCreateNew) {
                        Text("Nueva")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent  // deja ver tu fondo
                ),
                modifier = Modifier.background(Color.Transparent)
            )

            Spacer(Modifier.height(16.dp))

            Box(Modifier.fillMaxSize()) {
                when {
                    loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    else    -> {
                        // 3a) PagerState
                        val pagerState = rememberPagerState()

                        // 3b) Mapea cada Adventure a SectionCardItem, incluyendo la ruta de edición
                        val cardItems = adventures.map { adv ->
                            SectionCardItem(
                                title        = adv.title,
                                description  = adv.description,
                                imageResName = "adventure_default",
                                // aquí pones la ruta de edición de esa aventura
                                route        = AdventureFormGraph.createRoute(adv.id)
                            )
                        }

                        // 3c) Llama al carrusel con la firma correcta:
                        //     fun CardCarousel(cardItems, pagerState, navController)
                        CardCarousel(
                            cardItems,
                            pagerState,
                            navController
                        )
                    }
                }
            }
        }
    }
}
