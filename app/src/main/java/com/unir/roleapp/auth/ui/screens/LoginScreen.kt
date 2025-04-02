package com.roleapp.auth.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.common.CustomCircularProgressIndicator
import com.roleapp.core.ui.components.buttons.BackButton
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.auth.ui.screens.components.LoginForm
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.auth.viewmodels.UserState
import com.roleapp.auth.viewmodels.AuthViewModel


@Composable
fun LoginScreen() {
    MainLayout(){
        Column(){
            LoginBody()
            BackButton()
        }
    }
}

@Composable
fun LoginBody(
    viewModel: AuthViewModel = LocalAuthViewModel.current,
    characterViewModel: CharacterViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val navigationViewModel = LocalNavigationViewModel.current
    val context = LocalContext.current

    // En cuanto se complete el login sucederán dos cosas: 1. Se navegará a otra ruta. 2. Se deben cargar inmediatamente los personajes del usuario.
    LaunchedEffect(userState) {
        when (userState) {

            is UserState.LoggedOut -> {
                Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
            }

            is UserState.Success -> {
                navigationViewModel.navigate(ScreensRoutes.UserProfileScreen.route)
                characterViewModel.getCharactersByUser()
            }

            is UserState.Deleted -> {
                Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (userState) {
            is UserState.Loading -> CustomCircularProgressIndicator()
            is UserState.Error -> Toast.makeText(context, (userState as UserState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context, "¡Hola!", Toast.LENGTH_SHORT).show()
        }
    }
        LoginForm(viewModel::login, viewModel::signup)

}

