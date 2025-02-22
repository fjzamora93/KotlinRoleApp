package com.unir.sheet.ui.screens.character.items


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.sheet.data.model.Item
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.components.MenuMedievalButton
import com.unir.sheet.ui.screens.components.RegularCard
import com.unir.sheet.ui.screens.components.medievalButtonStyleSquare
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel
import com.unir.sheet.ui.viewmodels.ItemViewModel
import com.unir.sheet.util.CustomType

@Composable
fun ItemListScreen(){
    MainLayout(){
        Column(Modifier.fillMaxSize().padding(16.dp)
        ){
            ItemListBody()
            BackButton()
        }
    }
}



// LISTA DE PERSONAJES -> NO UTILIZAR DE MOMENTO
@Composable
fun ItemListBody(
    itemViewModel: ItemViewModel = hiltViewModel(),
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val selectedCharacter by characterViewModel.selectedCharacter.observeAsState()
    itemViewModel.getItems()
    val items by itemViewModel.itemList.observeAsState()
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (selectedCharacter != null)  {
            Text(
                text = "Oro disponible: ${selectedCharacter?.gold}",
                style = CustomType.bodyMedium
            )
        }


        items?.let {
            it.forEach { item ->
                ItemSummary(item)
            }
        }
    }
}

@Composable
fun ItemSummary(
    item: Item,
    itemViewModel: ItemViewModel = hiltViewModel(),
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current
) {

    RegularCard() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val currentCharacter = characterViewModel.selectedCharacter.value
            if (currentCharacter != null) {

                // Botón de comprar
                MenuMedievalButton(
                    onClick = {
                        if (characterViewModel.selectedCharacter.value!!.gold > item.goldValue) {
                            currentCharacter.gold -= item.goldValue
                            itemViewModel.addItemToCharacter(
                                currentCharacter = characterViewModel.selectedCharacter.value!!,
                                currentItem = item
                            )
                            characterViewModel.updateCharacter(currentCharacter)
                        }

                    },
                    modifier = medievalButtonStyleSquare(size = 50.dp),
                    icon = Icons.Default.MonetizationOn,
                    text = "Precio: ${item.goldValue}"
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ){
                Text(
                    text = item.name,
                    style = CustomType.titleMedium
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Daño: ${item.dice}",
                    style = CustomType.bodyMedium
                )
            }

        }
    }
}


