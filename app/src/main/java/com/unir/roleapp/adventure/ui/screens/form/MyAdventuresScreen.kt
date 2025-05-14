package com.unir.roleapp.adventure.ui.screens.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    onAdventureClick: (String) -> Unit,
    onCreateNew: () -> Unit,
    vm: MyAdventuresViewModel = hiltViewModel()
) {
    val adventures by vm.adventures.collectAsState()
    val loading    by vm.loading.collectAsState()
    val error      by vm.error.collectAsState()

    val textColor: Color = Color.White

    MainLayout {
        Column(Modifier.fillMaxSize()) {
            SmallTopAppBar(
                title = { Text("Mis Aventuras", color = textColor) },
                actions = {
                    Button(
                        onClick = onCreateNew,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = textColor
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text("Crear nueva")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.background(Color.Transparent)
            )

            Spacer(Modifier.height(16.dp))

            Box(Modifier.fillMaxSize()) {
                when {
                    loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    else    -> {

                        val pagerState = rememberPagerState()

                        val cardItems = adventures.map { adv ->
                            SectionCardItem(
                                title        = adv.title,
                                description  = adv.description,
                                imageResName = adv.acts.getOrNull(0)?.mapURL.orEmpty(),
                                route        = AdventureFormGraph.createRoute(adv.id)
                            )
                        }

                        CardCarousel(
                            cardItems,
                            pagerState,
                            navController,
                            true
                        )
                    }
                }
            }
        }
    }
}
