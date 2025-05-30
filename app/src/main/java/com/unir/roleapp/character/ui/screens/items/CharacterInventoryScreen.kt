package com.roleapp.character.ui.screens.items.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.ui.screens.common.DropDownText

import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.character.data.model.local.ItemCategory
import com.unir.roleapp.character.ui.screens.common.RectangularButton
import com.unir.roleapp.character.ui.screens.items.components.InventoryByCategorySection
import com.unir.roleapp.character.ui.screens.items.components.ItemForm
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

    var isEditingItem by remember { mutableStateOf(false) }

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
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "", tint = CustomColors.AshGray,
                        modifier = Modifier.size(40.dp).clickable { isEditingItem = true }
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


            InventoryByCategorySection(
                filteredItems = inventoryItems.filter{ item -> item.item.category == ItemCategory.EQUIPMENT },
                filter = "Equipo",
                onClick = {
                    itemViewModel.destroyItem(currentCharacter!!, it)
                }
            )

            InventoryByCategorySection(
                filteredItems = inventoryItems.filter{ item -> item.item.category == ItemCategory.WEAPON },
                filter = "Armas",
                onClick = {
                    itemViewModel.destroyItem(currentCharacter!!, it)
                }
            )

            InventoryByCategorySection(
                filteredItems = inventoryItems.filter{ item -> item.item.category == ItemCategory.CONSUMABLES },
                filter = "Consumibles",
                onClick = {
                    itemViewModel.destroyItem(currentCharacter!!, it)

                }
            )


            InventoryByCategorySection(
                filteredItems = inventoryItems.filter{ item -> item.item.category == ItemCategory.COMMON },
                filter = "Común",
                onClick = {
                    itemViewModel.destroyItem(currentCharacter!!, it)
                }
            )


            inventoryItems.forEach { detail ->


//                if (detail.quantity > 0 ){
//                    ItemSummaryComponent(
//                        item = detail.item,
//                        onClick = {
//                            itemViewModel.destroyItem(currentCharacter!!, detail.item)
//                                  } ,
//                        quantity = detail.quantity,
//
//                    )
//                    HorizontalDivider()
//                }
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

    if (isEditingItem) {
        Dialog(onDismissRequest = { isEditingItem = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.background,
                tonalElevation = 8.dp
            ) {
                ItemForm(
                    onDismiss = { isEditingItem = false },
                    onSave = { item ->
                        isEditingItem = false
                    }
                )
            }
        }
    }

}


