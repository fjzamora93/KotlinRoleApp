package com.roleapp.core.ui.components.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.unir.roleapp.R


val navigationItems = listOf(
    NavigationItem(
        title = "Nueva aventura",
        icon = Icons.Default.LocalLibrary,
        route = ScreensRoutes.TitleScreen.route
    ),
    NavigationItem(
        title = "Listado aventuras",
        icon = Icons.Default.Book,
        route = ScreensRoutes.MyAdventuresScreen.route
    ),
    NavigationItem(
        title = "Home",
        icon = Icons.Default.Home,
        route = ScreensRoutes.HomeScreen.route
    ),

    NavigationItem(
        title = "Personajes",
        icon = Icons.Default.SupervisedUserCircle,
        route = ScreensRoutes.CharacterListScreen.route
    ),
    NavigationItem(
        title = "Perfil",
        icon = Icons.Default.Settings,
        route = ScreensRoutes.UserProfileScreen.route
    )
)


@Composable
fun NavigationBar(

    containerColor: Color = colorResource(id = R.color.dark_blue),
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,

    ) {
    val currentRoute by navigationViewModel.currentRoute.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(containerColor)
            .padding(bottom = 10.dp, top = 0.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        navigationItems.forEach { item ->
            val isSelected = currentRoute == item.route // Comparamos con la ruta actual

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navigationViewModel.navigate(item.route) // Navegamos a la nueva ruta
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (isSelected) Color.White else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color.White else Color.Gray
                    )
                },
            )
        }

    }
}

@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    val backgroundColor = if (selected) indicatorColor.copy(alpha = 0.1f) else Color.Transparent

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (selected) indicatorColor else Color.Transparent,
                        shape = CircleShape
                    )
                    .padding(8.dp)
            ) {
                icon()
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (selected) label()
        }
    }
}