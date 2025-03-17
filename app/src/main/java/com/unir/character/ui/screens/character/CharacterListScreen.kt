package com.unir.character.ui.screens.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.unir.character.data.model.local.CharacterEntity
import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.di.LocalAuthViewModel
import com.navigation.NavigationViewModel
import com.unir.character.ui.screens.characterSheet.CharacterPortrait
import com.ui.components.BackButton
import com.ui.components.CustomIconButton
import com.ui.components.RegularCard
import com.ui.layout.MainLayout
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.auth.viewmodels.UserState
import com.unir.auth.viewmodels.AuthViewModel
import com.ui.theme.CustomType
import java.util.Locale


@Composable
fun CharacterListScreen(){
    MainLayout(){
        CharacterListBody()
    }
}




@Composable
fun CharacterListBody(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    authViewModel: AuthViewModel = LocalAuthViewModel.current,
) {
    val userState by authViewModel.userState.collectAsState()
    characterViewModel.getCharactersByUserId(userState.let { (it as UserState.Success).user.id!! })

    val characters by characterViewModel.characters.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        characters?.let {
            it.forEach { character ->
                CharacterSummary(
                    character,
                )
            }
        }
        BackButton()
    }
}

@Composable
fun CharacterSummary(
    character: CharacterEntity,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current

    ){
    var claseToString = character.rolClass.toString().lowercase(Locale.ROOT)
    var raceToString = character.race.toString().lowercase(Locale.ROOT)
    RegularCard(){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column( modifier = Modifier.weight(1f).wrapContentHeight()){
                CharacterPortrait(
                    character = character,
                )
            }

            Column(modifier = Modifier.weight(2f).wrapContentHeight() ){
                Text(
                    text = "${character.name}",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = " ${character.level} - ${raceToString}",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = " ${claseToString}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            CustomIconButton(
                text = "",
                icon = Icons.Default.Delete,
                onClick = { characterViewModel.deleteCharacter(character) }
            )
        }

    }

}

