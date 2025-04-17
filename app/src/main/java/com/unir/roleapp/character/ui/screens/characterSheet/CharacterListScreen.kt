package com.roleapp.character.ui.screens.characterSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.di.LocalAuthViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.layout.MainLayout
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.auth.viewmodels.AuthViewModel
import com.roleapp.character.data.model.local.Race
import com.roleapp.character.data.model.local.RolClass
import com.roleapp.character.ui.screens.characterSheet.components.CharacterPortrait
import com.roleapp.character.ui.screens.common.BottomDialogueMenu
import com.roleapp.core.ui.components.common.CustomCircularProgressIndicator
import com.unir.roleapp.R
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation
import androidx.compose.ui.res.stringResource

@Composable
fun CharacterListScreen(){
    MainLayout(
        floatingActionButton = { CreateCharacterButton() }
    ){
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp),
        text = stringResource(id = R.string.characters),
        style = MaterialTheme.typography.titleMedium,
        color = colorResource(id = R.color.white)
    )

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (characters.isEmpty()){
            CrossSwordsAnimation()
        }

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
    var showBottomSheet by remember { mutableStateOf(false) }

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




@Composable
fun CreateCharacterButton(
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Modificador para un fondo circular y tamaño grande
        IconButton(
            onClick = {
                navigationViewModel.navigate(ScreensRoutes.CharacterEditorScreen.createRoute(0))
            },
            modifier = Modifier
                .padding(16.dp)
                .size(80.dp) // Tamaño más grande para el botón
                .background(
                    color = MaterialTheme.colorScheme.primary, // Usa el color primario del tema
                    shape = CircleShape // Forma circular
                ),
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_person_add_alt_1_24),
                    contentDescription = "Add person icon",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        )
    }
}