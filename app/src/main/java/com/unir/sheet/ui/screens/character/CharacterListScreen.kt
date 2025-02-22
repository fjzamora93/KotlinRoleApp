package com.unir.sheet.ui.screens.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.ui.navigation.NavigationViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.screens.character.characterDetail.CharacterPortrait
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.components.MedievalDivider
import com.unir.sheet.ui.screens.components.NavigationButton
import com.unir.sheet.ui.screens.components.RegularCard
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel
import com.unir.sheet.util.CustomType
import java.util.Locale


@Composable
fun CharacterListScreen(){
    MainLayout(){
        CharacterListBody()
    }
}




@Composable
fun CharacterListBody(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth(),
) {
    val characters by characterViewModel.characters.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Lista de personajes: ", style = CustomType.titleLarge)
        characters?.let {
            it.forEach { character ->
                CharacterSummary(
                    character,
                    onDestroyItem = { characterViewModel.deleteCharacter(character) }
                )
            }
        }
        BackButton()
    }
}

@Composable
fun CharacterSummary(
    character: CharacterEntity,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    onDestroyItem: () -> Unit = {}
){
    var claseToString = character.rolClass.toString().lowercase(Locale.ROOT)

    RegularCard(){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column( modifier = Modifier.weight(1f).wrapContentHeight()){
                CharacterPortrait(
                    character = character,
                    context = LocalContext.current
                )
            }

            Column(modifier = Modifier.weight(2f).wrapContentHeight() ){
                Text(
                    text = "${character.name} - ${claseToString}",
                    style = CustomType.titleMedium
                )
            }
        }


        MedievalDivider()

        Row(

            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            NavigationButton(
                text = "Eliminar",
                icon = Icons.Default.Delete,
                onClick = { onDestroyItem() })

            NavigationButton(
                text = "Seleccionar",
                icon = Icons.Default.RemoveRedEye,
                onClick = {
                    navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(character.id!!))
                })
        }
    }

}

