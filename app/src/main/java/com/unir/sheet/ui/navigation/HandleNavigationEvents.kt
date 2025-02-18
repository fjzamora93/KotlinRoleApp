package com.unir.sheet.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController


@Composable
fun HandleNavigationEvents(
    navController: NavHostController,
    navigationViewModel: NavigationViewModel
) {
    val navigationEvent by navigationViewModel.navigationEvent.observeAsState()

    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            when (event) {
                is NavigationEvent.Navigate -> {
                    navController.navigate(event.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is NavigationEvent.NavigateAndPopUp -> {
                    navController.navigate(event.route) {
                        popUpTo(event.popUpToRoute) {
                            inclusive = event.inclusive
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            navigationViewModel.clearNavigationEvent()
        }
    }
}
