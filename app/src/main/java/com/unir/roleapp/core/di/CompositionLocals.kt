package com.roleapp.core.di

import androidx.compose.runtime.compositionLocalOf
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.auth.viewmodels.AuthViewModel


// CONSTANTES DE VIEWMODELS QUE VAN A SER COMPARTIDAS EN TODAS LAS PANTALLAS


val LocalNavigationViewModel = compositionLocalOf<NavigationViewModel> {
    error("NavigationViewModel no está disponible. Asegúrate de proporcionarlo.")
}



val LocalAuthViewModel = compositionLocalOf<AuthViewModel> {
    error("No hay usuario disponible en el contexto actual.")
}

