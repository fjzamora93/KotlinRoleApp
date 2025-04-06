package com.roleapp.auth.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.auth.ui.screens.components.LoginForm
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.auth.viewmodels.UserState
import com.roleapp.auth.viewmodels.AuthViewModel
import com.unir.roleapp.R


@Composable
fun LoginScreen() {
    MainLayout(){
        Column(){
            LoginHeader()
            LoginForm()
        }
    }
}


// La parte bonita de arriba solo estética, sin lógica)
@Composable
fun LoginHeader() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.d20_dice),
            contentDescription = "Logo de RolApp",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título principal
        androidx.compose.material3.Text(
            text = "RolApp",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF9F9F9)
            )
        )
        androidx.compose.material3.Text(
            text = "La app para gestionar juegos de rol",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFD4D4D8)),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))


    }

}

