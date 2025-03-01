package com.unir.sheet.di

import androidx.compose.runtime.compositionLocalOf
import com.unir.sheet.ui.navigation.NavigationViewModel
import com.unir.sheet.ui.viewmodels.CharacterViewModel
import com.unir.sheet.ui.viewmodels.UserState
import com.unir.sheet.ui.viewmodels.UserViewModel


// CONSTANTES DE VIEWMODELS QUE VAN A SER COMPARTIDAS EN TODAS LAS PANTALLAS


val LocalNavigationViewModel = compositionLocalOf<NavigationViewModel> {
    error("NavigationViewModel no está disponible. Asegúrate de proporcionarlo.")
}


val LocalCharacterViewModel = compositionLocalOf<CharacterViewModel> {
    error("LocalCharacterViewModel no está disponible. Asegúrate de proporcionarlo.")
}


val LocalUserViewModel = compositionLocalOf<UserViewModel> {
    error("No hay usuario disponible en el contexto actual.")
}