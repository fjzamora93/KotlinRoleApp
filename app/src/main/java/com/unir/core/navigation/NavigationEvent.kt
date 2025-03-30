package com.unir.core.navigation

sealed class NavigationEvent {
    data class Navigate(val route: String) : NavigationEvent()
    data class NavigateAndPopUp(val route: String, val popUpToRoute: String, val inclusive: Boolean) : NavigationEvent()
}