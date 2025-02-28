package com.unir.sheet.ui.screens.userscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.di.LocalUserViewModel
import com.unir.sheet.ui.navigation.NavigationViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel
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
fun UserProfileBody(
    viewModel: UserViewModel = LocalUserViewModel.current
) {
    val userState by viewModel.userState.collectAsState()

    when (userState) {
        is UserState.Loading -> {
            // Mostrar un loader
            CircularProgressIndicator()
        }
        is UserState.Success -> {
            UserProfileDetail()
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


@Composable
fun UserProfileDetail(
    viewModel: UserViewModel = LocalUserViewModel.current,
    navigation : NavigationViewModel = LocalNavigationViewModel.current
){
    val userState by viewModel.userState.collectAsState()

    val user = (userState as UserState.Success).user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Perfil de Usuario",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Nombre: ${user.name}",
            fontSize = 18.sp
        )
        Text(
            text = "Email: ${user.email}",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.logout()
                navigation.navigate(ScreensRoutes.LoginScreen.route)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Cerrar sesión",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cerrar sesión",
                fontSize = 16.sp,
                color = Color.White
            )
        }

    }
}