package com.roleapp.character.ui.screens.characterSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.buttons.MaxWidthButton
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.character.data.model.local.Race
import com.roleapp.character.data.model.local.RolClass
import com.roleapp.character.ui.screens.characterSheet.components.CharacterPortrait
import com.roleapp.character.ui.screens.common.BottomDialogueMenu
import com.unir.roleapp.R


@Composable
fun CharacterListScreen(){
    MainLayout(){
        CharacterListBody()
    }
}



@Composable
fun CharacterListBody(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = LocalAuthViewModel.current,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current
) {
    val userState by authViewModel.userState.collectAsState()
    characterViewModel.getCharactersByUser()

    val characters by characterViewModel.characters.collectAsState()

    Text(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 10.dp),
        text = "Personajes",
        style = MaterialTheme.typography.titleMedium,
        color = colorResource(id = R.color.white)
    )

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        characters.let {
            it.forEach { character -> CharacterSummary(character) }
        }

    }

    MaxWidthButton(
        label = "Crear personaje",
        onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterEditorScreen.createRoute(0)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterSummary(
    character: CharacterEntity,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
) {
    var claseToString: String = RolClass.getString(character.rolClass)
    var raceToString: String = Race.getString(character.race)
    var showBottomSheet by remember { mutableStateOf(false) } // Estado para mostrar el Bottom Sheet

    val textColor: Color = colorResource(id = R.color.white)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            CharacterPortrait(
                size = 120,
                character = character,
                onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(character.id)) }
            )
        }

        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = "${character.name} | ${character.level}",
                style = MaterialTheme.typography.titleMedium,
                color = textColor
            )
            Text(
                text = "$claseToString | $raceToString",
                style = MaterialTheme.typography.titleSmall,
                color = textColor
            )
        }

        IconButton(
            onClick = { showBottomSheet = true }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Más opciones",
                tint = textColor
            )
        }

    }

    HorizontalDivider()
    if (showBottomSheet) {
        BottomDialogueMenu(
            onDismiss = { showBottomSheet = false },
            onFirstOption = { navigationViewModel.navigate(ScreensRoutes.CharacterEditorScreen.createRoute(character.id)) },
            onSecondOption = { characterViewModel.deleteCharacter(character) }
        )
    }
}

