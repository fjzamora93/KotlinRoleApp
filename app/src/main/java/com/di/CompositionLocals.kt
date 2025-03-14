package com.di

import androidx.compose.runtime.compositionLocalOf
import com.navigation.NavigationViewModel
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.auth.viewmodels.AuthViewModel


// CONSTANTES DE VIEWMODELS QUE VAN A SER COMPARTIDAS EN TODAS LAS PANTALLAS


val LocalNavigationViewModel = compositionLocalOf<NavigationViewModel> {
    error("NavigationViewModel no está disponible. Asegúrate de proporcionarlo.")
}


val LocalCharacterViewModel = compositionLocalOf<CharacterViewModel> {
    error("LocalCharacterViewModel no está disponible. Asegúrate de proporcionarlo.")
}


val LocalAuthViewModel = compositionLocalOf<AuthViewModel> {
    error("No hay usuario disponible en el contexto actual.")
}

