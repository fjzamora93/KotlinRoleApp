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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.roleapp.core.ui.components.navigationbar.NavigationBar
import com.unir.roleapp.R

import kotlinx.coroutines.launch

/**
 * Composable plantilla, disponible para todas las screens, que incluye:
 * - Header.
 * - Menú lateral desplegable.
 * - Footer.
 * */
@Composable
fun MainLayout(
    floatingActionButton: @Composable () -> Unit = {},

    content: @Composable () -> Unit,
){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    MainMenu(
        drawerState = drawerState,
        onClose = {
            coroutineScope.launch { drawerState.close() }
        }
    ) {


        Scaffold(
            floatingActionButton = floatingActionButton,

            bottomBar = {
                NavigationBar()
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = listOf(Color(0xFF1F1D36), Color(0xFF3F3351))))
                    .padding(innerPadding)
            ) {
                /*Header(
                    onClickMenu = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )*/

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


