package com.unir.roleapp.auth.ui.screens.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roleapp.auth.data.model.User
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.unir.roleapp.R


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


        Text(
            text = "${user.email.split("@")[0]}",
            style = MaterialTheme.typography.titleLarge,
            color = textColor
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            color = textColor
        )

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
