package com.roleapp.character.ui.screens.items


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.roleapp.character.data.model.local.Item
import com.roleapp.core.ui.components.common.RegularCard
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.ui.theme.CustomType




// LISTA DE PERSONAJES -> NO UTILIZAR DE MOMENTO
@Composable
fun ShopComponent(
    itemViewModel: ItemViewModel = hiltViewModel(),
    characterViewModel: CharacterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    itemViewModel.getItemsBySession()
    val items by itemViewModel.itemList.collectAsState()
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Oro disponible: ${selectedCharacter?.gold}",
            style = CustomType.bodyMedium
        )



        items.let {
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
    characterViewModel: CharacterViewModel =hiltViewModel(),
) {

    RegularCard() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val currentCharacter by characterViewModel.selectedCharacter.collectAsState()
            if (currentCharacter != null) {

                // Botón de comprar
                IconButton(
                    onClick = {
                        itemViewModel.addItemToCharacter(item)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Shop,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
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


