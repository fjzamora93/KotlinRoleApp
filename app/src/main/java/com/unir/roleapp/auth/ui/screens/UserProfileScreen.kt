package com.roleapp.auth.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.buttons.BackButton
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.auth.viewmodels.AuthViewModel

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
        is com.roleapp.auth.viewmodels.UserState.Loading -> CircularProgressIndicator()
        is com.roleapp.auth.viewmodels.UserState.Success -> UserProfileDetail(user = (userState as com.roleapp.auth.viewmodels.UserState.Success).user)
        is com.roleapp.auth.viewmodels.UserState.Error -> Text("Error: ${(userState as com.roleapp.auth.viewmodels.UserState.Error).message}")
        is com.roleapp.auth.viewmodels.UserState.LoggedOut -> Text("Sesión cerrada")
        is com.roleapp.auth.viewmodels.UserState.Deleted -> Text("Cuenta eliminada")
        else -> Text("Usuario no registrado")
    }
}



@Composable
fun UserProfileDetail(
    user: com.roleapp.auth.data.model.User,
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
            style = MaterialTheme.typography.titleMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Nombre: ${user.id}",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "Email: ${user.email}",
            style = MaterialTheme.typography.titleSmall
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
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cerrar sesión",
                fontSize = 16.sp,
            )
        }

    }
}