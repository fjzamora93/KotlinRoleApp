package com.roleapp.auth.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.auth.data.model.User
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.buttons.BackButton
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.auth.viewmodels.UserState
import com.roleapp.core.di.LocalLanguageSetter
import com.roleapp.core.ui.components.common.DefaultColumn
import com.roleapp.core.ui.components.common.DefaultRow
import com.unir.roleapp.R
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation
import com.unir.roleapp.core.ui.components.common.MainBanner

@Composable
fun UserProfileScreen(
    selectedLang: String,
    onLanguageSelected: (String) -> Unit,
) {
    MainLayout(){
        Column(){
            MainBanner()

            UserProfileBody(selectedLang, onLanguageSelected)
            /*BackButton()*/
        }
    }
}

@Composable
fun UserProfileBody(
    selectedLang: String,
    onLanguageSelected: (String) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigation: NavigationViewModel = LocalNavigationViewModel.current
) {
    val userState by authViewModel.userState.collectAsState()

    when (userState) {
        is UserState.Loading -> DefaultColumn{  CrossSwordsAnimation() }
        is UserState.Success -> UserProfileDetail(
            user = (userState as UserState.Success).user,
            selectedLang = selectedLang,
            onLanguageSelected = onLanguageSelected
        )
        is UserState.Error -> Text("Error: ${(userState as UserState.Error).message}", style = MaterialTheme.typography.bodyMedium)
        is UserState.LoggedOut -> navigation.navigate(ScreensRoutes.LoginScreen.route)

        else -> navigation.navigate(ScreensRoutes.LoginScreen.route)
    }
}



@Composable
fun UserProfileDetail(
    selectedLang: String,
    onLanguageSelected: (String) -> Unit,
    user: User,
    viewModel: AuthViewModel = LocalAuthViewModel.current,
    navigation : NavigationViewModel = LocalNavigationViewModel.current
){
    val textColor: Color = colorResource(id = R.color.white);
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LanguageFlag(
                emoji = "🇪🇸",
                isSelected = selectedLang == "es" ,
                onClick = {
                    onLanguageSelected("es")
                }
            )

            LanguageFlag(
                emoji = "🇬🇧",
                isSelected = selectedLang == "en",
                onClick = {
                    onLanguageSelected("en")                }
            )
        }

        Text(
            text = stringResource(id = R.string.profile_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = textColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${user.id}",
            style = MaterialTheme.typography.titleSmall,
            color = textColor
        )
        Text(
            text = "${user.email}",
            style = MaterialTheme.typography.titleSmall,
            color = textColor
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
                text = stringResource(id = R.string.logout),
                fontSize = 16.sp,
            )
        }

    }
}



@Composable
fun LanguageFlag(
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderModifier = if (isSelected) {
        Modifier
            .border(2.dp, Color.White, shape = CircleShape)
            .padding(16.dp)
    } else {
        Modifier.padding(4.dp)
    }

    Text(
        text = emoji,
        fontSize = 32.sp,
        modifier = borderModifier
            .clip(CircleShape)
            .clickable { onClick() }
    )
}
