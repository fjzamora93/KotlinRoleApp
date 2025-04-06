package com.roleapp.auth.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.auth.viewmodels.UserState
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.common.CustomCircularProgressIndicator
import com.unir.roleapp.MyApplication.Companion.context
import com.unir.roleapp.R
import com.unir.roleapp.core.ui.components.common.MainBanner


@Composable
fun LoginScreen() {
    MainLayout(){
        Column(){
            MainBanner()
            LoginBody()
        }
    }
}



@Composable
fun LoginBody(
    authViewModel: AuthViewModel = hiltViewModel(),
    characterViewModel: CharacterViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
) {
    var email by remember { mutableStateOf("test_6@mail.com") }
    var password by remember { mutableStateOf("1234") }
    var showRegister by remember { mutableStateOf(false) }
    val textColor: Color  = colorResource(id = R.color.white)
    val secondTextColor: Color  = colorResource(id = R.color.gray)
    val userState by authViewModel.userState.collectAsState()

    val errorMessage by authViewModel.errorMessage.collectAsState()

    LaunchedEffect(showRegister) {
        authViewModel.clearErrorMessage()
    }

    LaunchedEffect(userState) {
        when (userState) {

            is UserState.LoggedOut -> {
                Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
            }

            is UserState.Success -> {
                navigationViewModel.navigate(ScreensRoutes.HomeScreen.route)
                characterViewModel.getCharactersByUser()
            }

            is UserState.Deleted -> {
                Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
            }

            else -> {}

        }

    }

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
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        Spacer(modifier = Modifier.height(16.dp))


        errorMessage?.let {
            Text(
                text = it,  // El mensaje de error
                color = Color.Red,  // Color del mensaje
                style = MaterialTheme.typography.labelMedium,  // Estilo del texto (puedes cambiarlo)
                modifier = Modifier.padding(start = 16.dp) // Padding para que no esté pegado al borde
            )
        }

        if (userState is UserState.Loading) {
            CustomCircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))


        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = secondTextColor) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(textColor = textColor),
            isError = errorMessage != null,
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = secondTextColor) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(textColor = textColor),
            isError = errorMessage != null,

            )


        if (showRegister) {
            var confirmPassword by remember { mutableStateOf("") }

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña", color = secondTextColor) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(textColor = textColor),
                isError = errorMessage != null,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { authViewModel.signup(email, password, confirmPassword) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
            ) {
                Text(text = "Registrarse", fontSize = 16.sp, color = Color.White)
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { authViewModel.login(email, password) },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.accent_primary))
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
            fontWeight = FontWeight.Medium,
            color = secondTextColor
        )

        TextButton(onClick = { showRegister = !showRegister }) {
            Text(
                text = if (showRegister) "Volver al login" else "Crear una cuenta",
                fontSize = 16.sp,
                color = textColor
            )
        }
    }
}

