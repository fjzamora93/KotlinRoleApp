package com.unir.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.di.LocalUserViewModel
import com.navigation.ScreensRoutes
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.viewmodels.CharacterViewModel
import com.unir.auth.viewmodels.UserState
import com.unir.auth.viewmodels.UserViewModel


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
    viewModel: UserViewModel = LocalUserViewModel.current,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current
) {
    val userState by viewModel.userState.collectAsState()
    val navigationViewModel = LocalNavigationViewModel.current

    // En cuanto se complete el login sucederán dos cosas: 1. Se navegará a otra ruta. 2. Se deben cargar inmediatamente los personajes del usuario.
    LaunchedEffect(userState) {
        if (userState is UserState.Success) {
            navigationViewModel.navigate(ScreensRoutes.UserProfileScreen.route)
            characterViewModel.getCharactersByUserId(userState.let { (it as UserState.Success).user.id!! })
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (userState) {
            is UserState.Loading -> CircularProgressIndicator()
            is UserState.Error -> {

                    Text("Error: ${(userState as UserState.Error).message}")
                    Button(onClick = { viewModel.login("email", "password") }) {
                        Text("Reintentar")
                    }
            }
    //        is UserState.LoggedOut -> Text("Sesión cerrada")
    //        is UserState.Deleted -> Text("Cuenta eliminada")
            else -> {
                // Aquí manejamos el estado Idle o cualquier otro que no esté relacionado con el login
                LoginForm(viewModel::login, viewModel::signup)
            }
        }
    }
}




@Composable
fun LoginForm(
    onLogin: (String, String) -> Unit,
    onRegister: (String, String, String) -> Unit
) {
    var email by remember { mutableStateOf("test_6@mail.com") }
    var password by remember { mutableStateOf("1234") }
    var showRegister by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (showRegister) "Crear Cuenta" else "Iniciar Sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (showRegister) {
            var confirmPassword by remember { mutableStateOf("") }

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onRegister(email, password, confirmPassword) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(text = "Registrarse", fontSize = 16.sp, color = Color.White)
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onLogin(email, password) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text(text = "Iniciar sesión", fontSize = 16.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Divider(modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Eres nuevo?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        TextButton(onClick = { showRegister = !showRegister }) {
            Text(
                text = if (showRegister) "Volver al login" else "Crear una cuenta",
                fontSize = 16.sp,
                color = Color.Blue
            )
        }
    }
}
