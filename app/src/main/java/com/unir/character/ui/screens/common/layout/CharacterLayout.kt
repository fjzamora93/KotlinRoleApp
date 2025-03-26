package com.unir.character.ui.screens.common.layout

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
import androidx.compose.ui.unit.dp
import com.ui.components.navigationbar.NavigationBar
import kotlinx.coroutines.launch



@Composable
fun CharacterLayout(
    onToggleMenu: () -> Unit,
    content: @Composable () -> Unit
){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    CharacterMenu(drawerState = drawerState, onClose = { coroutineScope.launch { drawerState.close() } }) {
        Scaffold(bottomBar = { NavigationBar() }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CharacterHeader(onClickMenu = { coroutineScope.launch { drawerState.open() } })

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