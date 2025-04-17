package com.roleapp.character.ui.screens.common.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.core.ui.components.navigationbar.NavigationBar
import kotlinx.coroutines.launch



@Composable
fun CharacterLayout(
    content: @Composable () -> Unit
    ){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()



    CharacterMenu(drawerState = drawerState, onClose = { coroutineScope.launch { drawerState.close() } }) {
        Scaffold(
            bottomBar = { NavigationBar() },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CharacterHeader(
                    onClickMenu = { coroutineScope.launch { drawerState.open() } },
                )

                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    item {
                        content()
                    }
                }
            }
        }
    }
}