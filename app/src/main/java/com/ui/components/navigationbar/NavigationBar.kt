package com.ui.components.navigationbar

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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes


val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        icon = Icons.Default.Home,
        route = ScreensRoutes.MainScreen.route
    ),
    NavigationItem(
        title = "Perfil",
        icon = Icons.Default.Person,
        route = ScreensRoutes.LoginScreen.route
    ),
    NavigationItem(
        title = "Personajes",
        icon = Icons.Default.Create,
        route = ScreensRoutes.CharacterListScreen.route
    ),
    NavigationItem(
        title = "Setting",
        icon = Icons.Default.Settings,
        route = ScreensRoutes.FontTemplateScreen.route
    )
)


@Composable
fun NavigationBar(
    containerColor: Color = Color.White,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    modifier: Modifier = Modifier
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    // Usar un Row para organizar los ítems horizontalmente
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(containerColor)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navigationViewModel.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (selectedNavigationIndex.intValue == index)
                            Color.Black // Color cuando está seleccionado
                        else Color.Gray // Color cuando no está seleccionado
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (selectedNavigationIndex.intValue == index)
                            Color.Black // Color cuando está seleccionado
                        else Color.Gray // Color cuando no está seleccionado
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
    containerColor: Color = Color.White,
    selectedIconColor: Color = MaterialTheme.colorScheme.surface,
    selectedTextColor: Color = Color.Black,
    unselectedTextColor: Color = Color.Gray,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    val backgroundColor = if (selected) indicatorColor.copy(alpha = 0.1f) else Color.Transparent
    val iconColor = if (selected) selectedIconColor else unselectedTextColor

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
            label()
        }
    }
}