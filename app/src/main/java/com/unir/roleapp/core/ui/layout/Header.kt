package com.roleapp.core.ui.layout


import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.auth.viewmodels.UserState
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.core.ui.theme.CustomColors

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onClickMenu : () -> Unit
) {

    HeaderBody( onClickMenu = onClickMenu )
    HorizontalDivider(
        Modifier
            .background(Color(0xFFEEEEEE))
            .height(1.dp)
            .fillMaxWidth())

}


@Composable
fun HeaderBody(
    modifier: Modifier = Modifier,
    onClickMenu : () -> Unit,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    navigationViewModel : NavigationViewModel = LocalNavigationViewModel.current
){
    val activity = LocalContext.current as Activity
    val navTitle by navigationViewModel.navTitle.collectAsState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserThombnail()

        Text(
            text = navTitle ?: "",
            style = MaterialTheme.typography.titleLarge,
        )

        // Cerrar la aplicación
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close app",
            modifier = Modifier
                .clickable { activity.finish() }
        )
    }
}

// MINIATURA DEL PERSONAJE
@Composable
fun UserThombnail(
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = LocalAuthViewModel.current
){
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    val userState by authViewModel.userState.collectAsState()
    val user = when (userState){
        is UserState.Success -> (userState as UserState.Success).user
        else -> null
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        IconButton(
            onClick = {
                if (selectedCharacter != null) {
                    navigationViewModel.navigate(
                        ScreensRoutes.CharacterDetailScreen.createRoute(
                            selectedCharacter!!.id))
                } else {
                    navigationViewModel.navigate(ScreensRoutes.UserProfileScreen.route)
                }
            }
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Detail",
                modifier = Modifier
                    .size(60.dp)
                    .shadow(4.dp, CircleShape),
                tint = CustomColors.IronDark
            )
        }

    }
}