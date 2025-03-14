package com.unir.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import com.unir.auth.data.model.User
import com.di.LocalNavigationViewModel
import com.di.LocalAuthViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes
import com.ui.components.BackButton
import com.ui.layout.MainLayout
import com.unir.auth.viewmodels.UserState
import com.unir.auth.viewmodels.AuthViewModel

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
    authViewModel: AuthViewModel = LocalAuthViewModel.current
) {
    val userState by authViewModel.userState.collectAsState()

    when (userState) {
        is UserState.Loading -> CircularProgressIndicator()
        is UserState.Success -> UserProfileDetail(user = (userState as UserState.Success).user)
        is UserState.Error -> Text("Error: ${(userState as UserState.Error).message}")
        is UserState.LoggedOut -> Text("Sesión cerrada")
        is UserState.Deleted -> Text("Cuenta eliminada")
        else -> Text("Usuario no registrado")
    }
}



@Composable
fun UserProfileDetail(
    user: User,
    viewModel: AuthViewModel = LocalAuthViewModel.current,
    navigation : NavigationViewModel = LocalNavigationViewModel.current
){

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
            text = "Nombre: ${user.id}",
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
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
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