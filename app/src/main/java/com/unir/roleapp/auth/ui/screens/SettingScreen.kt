package com.roleapp.auth.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.auth.viewmodels.UserState
import com.roleapp.core.ui.components.common.DefaultColumn
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.auth.ui.screens.components.ActiveSettingDialog
import com.unir.roleapp.auth.ui.screens.components.DeveloperModeDialog
import com.unir.roleapp.auth.ui.screens.components.IndependentButton
import com.unir.roleapp.auth.ui.screens.components.LanguageDialog
import com.unir.roleapp.auth.ui.screens.components.PrivacyDialog
import com.unir.roleapp.auth.ui.screens.components.SettingsRow
import com.unir.roleapp.auth.ui.screens.components.UserProfileDialog
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation

@Composable
fun UserProfileScreen(
    selectedLang: String,
    onLanguageSelected: (String) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigation: NavigationViewModel = LocalNavigationViewModel.current
    ) {
    val userState by authViewModel.userState.collectAsState()

    MainLayout(){
        Column(){

            when (userState) {
                is UserState.Loading -> DefaultColumn{  CrossSwordsAnimation() }

                is UserState.Success -> {
                    SettingsScreen(
                        selectedLang = selectedLang,
                        onLanguageSelected = onLanguageSelected
                   )

//                    UserProfileDetail(
//                        user = (userState as UserState.Success).user,
//                        selectedLang = selectedLang,
//                        onLanguageSelected = onLanguageSelected
//                    )
                }
                is UserState.Error -> {
                    navigation.navigate(ScreensRoutes.LoginScreen.route)
                }

                is UserState.LoggedOut -> navigation.navigate(ScreensRoutes.LoginScreen.route)

                else -> {

                }
            }
        }
    }
}




@Composable
fun SettingsScreen(
    selectedLang: String,
    onLanguageSelected: (String) -> Unit,

    authViewModel: AuthViewModel = LocalAuthViewModel.current,
    navigation: NavigationViewModel = LocalNavigationViewModel.current,

) {
    var activeDialog by remember { mutableStateOf<ActiveSettingDialog>(ActiveSettingDialog.None) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Ajustes de la cuenta",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))
        // Primer bloque
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.DarkGray)
                .padding(vertical = 8.dp)
        ) {
            SettingsRow(
                icon = Icons.Default.Person,
                text = "Cuenta",
                onClick = { activeDialog = ActiveSettingDialog.User }
            )

            HorizontalDivider()

            SettingsRow(
                icon = Icons.Default.Security,
                text = "Datos y privacidad",
                onClick = { activeDialog = ActiveSettingDialog.Privacy }
            )

            HorizontalDivider()

            SettingsRow(
                icon = Icons.Default.Language,
                text = "Idioma",
                onClick = {
                    activeDialog = ActiveSettingDialog.Language
                }
            )

            HorizontalDivider()

            SettingsRow(
                icon = Icons.Default.DeveloperMode,
                text = "Modo desarrollador",
                onClick = { activeDialog = ActiveSettingDialog.Develop }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Segundo bloque (cerrar sesión)
        IndependentButton(
            icon = Icons.Default.ExitToApp,
            text = "Cerrar sesión",
            onClick = {
                authViewModel.logout()
                navigation.navigate(ScreensRoutes.LoginScreen.route)

                      },
            textColor = CustomColors.LIGHTRED
        )
    }

    when (activeDialog) {

        is ActiveSettingDialog.User ->{
            UserProfileDialog(
                onEditClick = { },
                onDismiss = { activeDialog = ActiveSettingDialog.None}
            )
        }

        is ActiveSettingDialog.Language -> {
            LanguageDialog(
                selectedLang = selectedLang,
                onLanguageSelected = onLanguageSelected,
                onDismiss = { activeDialog = ActiveSettingDialog.None }
            )
        }

        is ActiveSettingDialog.Develop -> {
            DeveloperModeDialog(
                isDevModeEnabled = true,
                onDevModeToggle = { /* Handle developer mode toggle */ },
                onDismiss = { activeDialog = ActiveSettingDialog.None }
            )
        }

        is ActiveSettingDialog.Privacy -> {
            PrivacyDialog(
                useDataForExperience = true,
                onUseDataForExperienceToggle = { },
                useDataForImprovement = true,
                onUseDataForImprovementToggle = {  },
                onDismiss = {  activeDialog = ActiveSettingDialog.None }
            )
        }

        is ActiveSettingDialog.None -> {

        }
    }

}


