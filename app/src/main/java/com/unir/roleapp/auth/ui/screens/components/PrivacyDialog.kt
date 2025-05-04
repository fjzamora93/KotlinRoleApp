package com.unir.roleapp.auth.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
import com.roleapp.character.ui.screens.common.dialogues.CharacterDialog
import com.roleapp.core.di.LocalLanguageSetter
import com.roleapp.core.ui.components.common.DefaultColumn
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.R
import com.unir.roleapp.auth.ui.screens.components.IndependentButton
import com.unir.roleapp.auth.ui.screens.components.LanguageDialog
import com.unir.roleapp.auth.ui.screens.components.SettingsRow
import com.unir.roleapp.auth.ui.screens.components.UserProfileDetail
import com.unir.roleapp.character.ui.screens.common.InfoDialog
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation
import com.unir.roleapp.core.ui.components.common.MainBanner

@Composable
fun PrivacyDialog(
    useDataForExperience: Boolean,
    onUseDataForExperienceToggle: (Boolean) -> Unit,
    useDataForImprovement: Boolean,
    onUseDataForImprovementToggle: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(
                text = "Datos y privacidad",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Switch 1: Experiencia
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Usar datos para mejorar mi experiencia",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = useDataForExperience,
                    onCheckedChange = { onUseDataForExperienceToggle(it) }
                )
            }
            Text(
                text = "Utilizamos tus datos para ofrecerte contenido más relevante y personalizado dentro de la aplicación.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Switch 2: Mejorar RoleApp
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Usar datos para mejorar RoleApp",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = useDataForImprovement,
                    onCheckedChange = { onUseDataForImprovementToggle(it) }
                )
            }
            Text(
                text = "Ayúdanos a mejorar RoleApp permitiéndonos recopilar datos anónimos sobre el uso de la aplicación.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
