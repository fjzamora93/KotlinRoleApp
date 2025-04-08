package com.unir.roleapp.character.ui.screens.items.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.Item
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.ui.components.common.RegularCard


@Composable
fun InventoryItemCard(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    itemViewModel: ItemViewModel = hiltViewModel(),
    item: Item,
    quantity: Int,
    modifier: Modifier = Modifier
) {
    val character by characterViewModel.selectedCharacter.collectAsState()

    RegularCard() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${item.id} - ${item.name}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Precio: ${item.goldValue} + Cantidad $quantity",
                style = MaterialTheme.typography.labelSmall
            )

            Row(){

                Button(onClick = {
                    itemViewModel.destroyItem(character!!, item)
                }) {
                    Text(text = "consumir / destruir")
                }
            }

        }
    }
}