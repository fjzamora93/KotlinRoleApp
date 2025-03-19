package com.unir.character.ui.screens.characterSheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.RolClass
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes
import com.unir.sheet.R
import java.util.Locale

@Composable
fun CharacterPortrait(
    character: CharacterEntity,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    onClick : () -> Unit = {  },
    size:Int = 120,
    ) {
    val imageName = "${character.race}_${character.rolClass}_${character.gender}.png".lowercase()
    val imageResId = rememberImageResource(imageName)

    println("ImagenResource: $imageName")
    // Verificar si el recurso existe
    val painter = if (imageResId != 0) {
        painterResource(id = imageResId)
    } else {

        when (character.rolClass) {
            RolClass.NULL -> painterResource(id = R.drawable.fallback_image)
            RolClass.WARRIOR -> painterResource(id = R.drawable.human_warrior_male)
            RolClass.BARD -> painterResource(id = R.drawable.human_bard_male)
            RolClass.ROGUE -> painterResource(id = R.drawable.human_rogue_male)
            RolClass.EXPLORER -> painterResource(id = R.drawable.human_explorer_male)
            RolClass.CLERIC -> painterResource(id = R.drawable.human_cleric_male)
            RolClass.PALADIN -> painterResource(id = R.drawable.human_warrior_male)
            RolClass.SORCERER, RolClass.WARLOCK -> painterResource(id = R.drawable.human_wizard_male)
            RolClass.WIZARD -> painterResource(id = R.drawable.human_wizard_male)
            RolClass.DRUID -> painterResource(id = R.drawable.human_warrior_male)
            RolClass.MONK -> painterResource(id = R.drawable.human_warrior_male)
            RolClass.BARBARIAN -> painterResource(id = R.drawable.human_warrior_male)
        }
    }


    Image(
        painter = painter,
        contentDescription = "Título no disponible: $imageName",
        modifier = Modifier
            .width(size.dp)
            .padding(16.dp)
            .clickable(onClick = { onClick() })
    )
}

@Composable
fun rememberImageResource(imageName: String): Int {
    val context = LocalContext.current
    return remember(imageName) {
        context.resources.getIdentifier(
            imageName.lowercase(Locale.ROOT),
            "drawable",
            context.packageName
        )
    }
}
