package com.unir.sheet.ui.screens.userscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.viewmodels.UserState
import com.unir.sheet.ui.viewmodels.UserViewModel

@Composable
fun LoginScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    val userState by viewModel.userState.collectAsState()
    val navigationViewModel = LocalNavigationViewModel.current

    LaunchedEffect(userState) {
        if (userState is UserState.Success) {
            navigationViewModel.navigate(ScreensRoutes.MainScreen.route)
        }
    }

    when (userState) {
        is UserState.Idle -> LoginForm(viewModel::login)
        is UserState.Loading -> CircularProgressIndicator()
        is UserState.Error -> {
            Column {
                Text("Error: ${(userState as UserState.Error).message}")
                Button(onClick = { viewModel.login("email", "password") }) {
                    Text("Reintentar")
                }
            }
        }
        is UserState.LoggedOut -> Text("Sesión cerrada")
        is UserState.Deleted -> Text("Cuenta eliminada")
        else -> {}
    }
}


@Composable
fun LoginForm(onLogin: (String, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") }, visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = { onLogin(email, password) }) {
            Text("Iniciar sesión")
        }
    }
}
