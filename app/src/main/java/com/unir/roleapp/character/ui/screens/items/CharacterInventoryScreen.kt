package com.roleapp.character.ui.screens.items.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.Item
import com.roleapp.character.ui.screens.common.BottomDialogueMenu
import com.roleapp.character.ui.screens.common.DropDownText
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel

import com.roleapp.character.ui.screens.common.layout.CharacterLayout

import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.core.ui.theme.MedievalColours
import com.unir.roleapp.character.data.model.local.ItemCategory
import com.unir.roleapp.character.ui.screens.common.RectangularButton
import com.unir.roleapp.character.ui.screens.items.components.ItemSummaryComponent



@Composable
fun CharacterInventoryBody(
    itemViewModel: ItemViewModel = hiltViewModel(),
    characterViewModel: CharacterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val inventoryItems by itemViewModel.itemsByCharacter.collectAsState()
    val isLoading by itemViewModel.loadingState.collectAsState(false)
    val shopItems by itemViewModel.itemList.collectAsState()
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()
    var isInventory by remember { mutableStateOf(true) }
    var currentGold by remember { mutableIntStateOf(currentCharacter!!.gold) }

    var selectedCategory by remember { mutableStateOf(ItemCategory.ALL) }

    val filteredItems = shopItems.filter { item ->
        item.category == selectedCategory || selectedCategory == ItemCategory.ALL
    }

    LaunchedEffect (inventoryItems) {
        itemViewModel.calculateStatsFromItems()
    }

    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RectangularButton(
                modifier = Modifier.weight(1f),
                text = "Inventario",
                icon = Icons.Default.Backpack,
                isEnabled = isInventory,
                onClick = { isInventory = true }
            )

            RectangularButton(
                modifier = Modifier.weight(1f),
                text = "Tienda",
                icon = Icons.Default.ShoppingBag,
                isEnabled = !isInventory,
                onClick = { isInventory = false }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Column(modifier = Modifier.weight(4f),){
                DefaultRow {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = "", tint = MedievalColours.Gold,
                        modifier = Modifier.size(40.dp)
                    )

                    TextField(
                        value = currentGold.toString(),
                        onValueChange = { newValue ->
                            currentGold = newValue.toIntOrNull() ?: 0
                            val updatedCharacter = currentCharacter!!.copy( gold = currentGold )
                            characterViewModel.saveCharacter(updatedCharacter)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("") }
                    )
                }
            }

            if (!isInventory){
                Column(modifier = Modifier.weight(5f),) {
                    DefaultRow{
                        DropDownText(
                            label = "Categoría",
                            options = ItemCategory.getListOf(),
                            selectedOption = ItemCategory.getString(selectedCategory),
                            onValueChange = { newValue ->
                                selectedCategory = ItemCategory.getItemCategory(newValue)
                            }
                        )

                    }
                }
            }
        }


        if (isLoading) {
            CircularProgressIndicator()
        } else if (isInventory){
            inventoryItems.forEach { detail ->
                if (detail.quantity > 0 ){
                    ItemSummaryComponent(
                        item = detail.item,
                        onClick = {
                            itemViewModel.destroyItem(currentCharacter!!, detail.item)
                                  } ,
                        quantity = detail.quantity,

                    )
                    HorizontalDivider()
                }
            }
        } else {
            filteredItems.forEach { item ->

                ItemSummaryComponent(
                    item = item,
                    isSelling = true,
                    onClick = {
                        itemViewModel.addItemToCharacter(item)
                        currentGold -= item.goldValue
                        val updatedCharacter = currentCharacter!!.copy(
                            gold = currentGold
                        )
                        characterViewModel.saveCharacter(updatedCharacter)
                    }
                )

                HorizontalDivider()

            }
        }
    }
}


