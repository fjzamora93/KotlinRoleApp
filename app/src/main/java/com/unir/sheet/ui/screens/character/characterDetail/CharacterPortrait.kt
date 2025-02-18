package com.unir.sheet.ui.screens.character.characterDetail

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import com.unir.sheet.data.local.model.RolCharacter
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.ui.navigation.NavigationViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.viewmodels.CharacterViewModel
import java.util.Locale


@Composable
fun CharacterPortrait(
    context: Context,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    character: RolCharacter
) {
    val selectedCharacter by characterViewModel.selectedCharacter.observeAsState()

    val imageName = "${character.race}-${character.rolClass}-${character.gender}"
    val imagePath = "file:///android_asset/portraits/${imageName}.png"



    AsyncImage(
        model = imagePath.lowercase(Locale.ROOT),
        contentDescription = "Título no disponible",
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable(onClick = {
                println(imagePath)
                navigationViewModel.navigate(
                    ScreensRoutes.CharacterDetailScreen.createRoute(
                        character.id
                    )
                )
            })
    )

}
