package com.unir.auth.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.di.LocalAuthViewModel
import com.navigation.ScreensRoutes
import com.ui.components.BackButton
import com.ui.components.CustomCircularProgressIndicator
import com.ui.layout.MainLayout
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.auth.viewmodels.UserState
import com.unir.auth.viewmodels.AuthViewModel


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
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current
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
                characterViewModel.getCharactersByUserId(userState.let { (it as UserState.Success).user.id!! })
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

