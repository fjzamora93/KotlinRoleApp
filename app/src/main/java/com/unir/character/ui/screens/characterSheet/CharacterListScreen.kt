package com.unir.character.ui.screens.characterSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.character.data.model.local.CharacterEntity
import com.unir.core.di.LocalNavigationViewModel
import com.unir.core.di.LocalAuthViewModel
import com.unir.core.navigation.NavigationViewModel
import com.unir.core.navigation.ScreensRoutes
import com.unir.core.ui.components.buttons.MaxWidthButton
import com.unir.core.ui.layout.MainLayout
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.auth.viewmodels.AuthViewModel
import com.unir.character.data.model.local.Race
import com.unir.character.data.model.local.RolClass
import com.unir.character.ui.screens.characterSheet.components.CharacterPortrait
import com.unir.character.ui.screens.common.BottomDialogueMenu


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

    MaxWidthButton(
        label = "Crear personaje",
        onClick = { navigationViewModel.navigate(ScreensRoutes.CharacterEditorScreen.createRoute(0)) },
    )

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        characters.let {
            it.forEach { character -> CharacterSummary(character) }
        }

    }
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
            )
            Text(
                text = "$claseToString | $raceToString",
                style = MaterialTheme.typography.titleSmall
            )
        }

        IconButton(
            onClick = { showBottomSheet = true }
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Más opciones"
            )
        }

    }

    HorizontalDivider()
    if (showBottomSheet) {
        BottomDialogueMenu(
            onDismiss = { showBottomSheet = false },
            onEdit = { navigationViewModel.navigate(ScreensRoutes.CharacterEditorScreen.createRoute(character.id)) },
            onDelete = { characterViewModel.deleteCharacter(character) }
        )
    }
}

