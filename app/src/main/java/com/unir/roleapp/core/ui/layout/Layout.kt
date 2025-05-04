package com.roleapp.core.ui.layout


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.navigationbar.NavigationBar
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.R

import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
/**
 * Composable plantilla, disponible para todas las screens, que incluye:
 * - Header.
 * - Menú lateral desplegable.
 * - Footer.
 * */
@Composable
fun MainLayout(
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,

    floatingActionButton: @Composable () -> Unit = {},

    content: @Composable () -> Unit,

    ){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val currentRoute by navigationViewModel.currentRoute.collectAsState()

    MainMenu(
        drawerState = drawerState,
        onClose = {
            coroutineScope.launch { drawerState.close() }
        }
    ) {


        Scaffold(
            floatingActionButton = floatingActionButton,
            bottomBar = {
                if (currentRoute != ScreensRoutes.LoginScreen.route) {
                    NavigationBar()
                }
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CustomColors.ThemeGradient)
                    .padding(innerPadding)
            ) {

                // CONTENIDO DEL SCREEN. MODIFICAR SI FUESE NECESARIO.
                LazyColumn(Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp , horizontal = 0.dp)
                ){
                    item {
                        content()
                    }
                }
            }
        }


    }
}


