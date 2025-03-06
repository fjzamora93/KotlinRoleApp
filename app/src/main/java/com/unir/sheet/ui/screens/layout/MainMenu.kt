package com.unir.sheet.ui.screens.layout


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.di.LocalUserViewModel
import com.unir.sheet.ui.navigation.NavigationViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.viewmodels.UserState
import com.unir.sheet.ui.viewmodels.UserViewModel
import com.unir.sheet.util.MedievalColours


@Composable
fun MainMenu(
    drawerState: DrawerState,
    onClose: () -> Unit,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    userViewModel: UserViewModel = LocalUserViewModel.current,
    content: @Composable () -> Unit,
) {
    val user by userViewModel.userState.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column {

                    MenuOption(
                        text = "Menú principal",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.MainScreen.route) },
                        icon = Icons.Default.Home
                    )

                    MenuOption(
                        text = "Login",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.LoginScreen.route) },
                        icon = Icons.AutoMirrored.Filled.Login
                    )


                    MenuOption(
                        text = "Perfil",
                        onClick = { navigationViewModel.navigate(ScreensRoutes.UserProfileScreen.route) },
                        icon = Icons.Default.Person
                    )

                    if (user is UserState.Success){
                        MenuOption(
                            text = "Personajes",
                            onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterListScreen.route) },
                            icon = Icons.Default.Person
                        )

                        MenuOption(
                            text = "Items",
                            onClick = { navigationViewModel.navigate(ScreensRoutes.ItemListScreen.route) },
                            icon = Icons.Default.Architecture
                        )


                        MenuOption(
                            text = "Creación personaje",
                            onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterCreatorScreen.route) },
                            icon = Icons.Default.EditOff
                        )

                        MenuOption(
                            text = "Tipografías y fuentes",
                            onClick = { navigationViewModel.navigate(ScreensRoutes.FontTemplateScreen.route) },
                            icon = Icons.Default.FontDownload
                        )

                    }



                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onClose) {
                        Text("Cerrar")
                    }
                }
            }
        },
        content = content
    )
}



@Composable
fun MenuOption(
    text: String,
    onClick: () -> Unit,
    icon : ImageVector = Icons.Default.Home,
    color: Color = MedievalColours.IronDark,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFD6D6D6))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(30.dp),
            imageVector = icon,
            contentDescription = "",
            tint = color
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = Color.Black
        )
    }
}