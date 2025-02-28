package com.unir.sheet.ui.screens.userscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.UserState
import com.unir.sheet.ui.viewmodels.UserViewModel

@Composable
fun UserProfileScreen() {
    MainLayout(){
        Column(){
            UserProfileBody()
            BackButton()
        }
    }
}

@Composable
fun UserProfileBody(viewModel: UserViewModel = hiltViewModel()) {
    val userState by viewModel.userState.collectAsState()

    when (userState) {
        is UserState.Loading -> {
            // Mostrar un loader
            CircularProgressIndicator()
        }
        is UserState.Success -> {
            val user = (userState as UserState.Success).user
            Text("Email: ${user.email}") // Aquí mostrarías el correo
        }
        is UserState.Error -> {
            val errorMessage = (userState as UserState.Error).message
            Text("Error: $errorMessage")
        }
        is UserState.LoggedOut -> {
            Text("Sesión cerrada")
        }
        is UserState.Deleted -> {
            Text("Cuenta eliminada")
        }
        else -> {
            // Estado Idle (sin login)
            Text("Usuario no registrado")
        }
    }
}
