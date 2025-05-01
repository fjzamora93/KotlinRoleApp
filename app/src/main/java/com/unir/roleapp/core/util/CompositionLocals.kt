package com.unir.roleapp.core.di

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.unir.roleapp.core.navigation.NavigationViewModel
import com.unir.roleapp.auth.viewmodels.AuthViewModel


// CONSTANTES DE VIEWMODELS QUE VAN A SER COMPARTIDAS EN TODAS LAS PANTALLAS


val LocalNavigationViewModel = compositionLocalOf<NavigationViewModel> {
    error("NavigationViewModel no está disponible. Asegúrate de proporcionarlo.")
}


val LocalAuthViewModel = compositionLocalOf<AuthViewModel> {
    error("No hay usuario disponible en el contexto actual.")
}

val LocalLanguageSetter = staticCompositionLocalOf<(String) -> Unit> {
    error("No language setter provided")
}

