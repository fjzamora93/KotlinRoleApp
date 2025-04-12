package com.roleapp.character.ui.screens.items.components

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
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel

import com.roleapp.character.ui.screens.common.layout.CharacterLayout

import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.roleapp.core.ui.theme.MedievalColours
import com.unir.roleapp.character.ui.screens.items.components.CurrentGoldComponent
import com.unir.roleapp.character.ui.screens.common.RectangularButton
import com.unir.roleapp.character.ui.screens.items.components.ItemSummaryComponent
import com.unir.roleapp.core.ui.components.animations.CrossSwordsAnimation


@Composable
fun CharacterInventoryScreen(
    characterViewModel: CharacterViewModel = hiltViewModel(),
){

    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()

    // Obtener datos cuando se monta la pantalla
    LaunchedEffect(selectedCharacter) {
        characterViewModel.getActiveCharacter()
    }

    CharacterLayout {
        selectedCharacter?.let {
            CharacterInventoryBody()
        } ?: CrossSwordsAnimation()
    }
}


@Composable
fun CharacterInventoryBody(
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    itemViewModel: ItemViewModel = hiltViewModel(),
    characterViewModel: CharacterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val inventoryItems by itemViewModel.itemsByCharacter.collectAsState()
    val isLoading by itemViewModel.loadingState.collectAsState(false)
    val shopItems by itemViewModel.itemList.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()

    var isInventory by remember { mutableStateOf(true) }

    // Animar los pesos
    val inventoryWeight by animateFloatAsState(
        targetValue = if (isInventory) 0.7f else 1.5f,
        animationSpec = tween(durationMillis = 300)
    )

    val shopWeight by animateFloatAsState(
        targetValue = if (isInventory) 1.5f else 0.7f,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(showBottomSheet){
        characterViewModel.getActiveCharacter()
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
                modifier = Modifier.weight(inventoryWeight),
                text = "Inventario",
                icon = Icons.Default.Inventory,
                isEnabled = !isInventory,
                onClick = { isInventory = true }
            )

            RectangularButton(
                modifier = Modifier.weight(shopWeight),
                text = "Tienda",
                icon = Icons.Default.ShoppingBag,
                isEnabled = isInventory,
                onClick = { isInventory = false }
            )
        }

        // ORO DISPONIBLE
        var goldText by remember { mutableStateOf(currentCharacter?.gold?.toString() ?: "0") }

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = modifier
        ){
            Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = "", tint = MedievalColours.Gold, modifier = Modifier.size(40.dp))

            TextField(
                value = goldText,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        goldText = newValue
                        val currentCharacter = characterViewModel.selectedCharacter.value
                        currentCharacter!!.gold = newValue.toIntOrNull() ?: 0
                        characterViewModel.saveCharacter(currentCharacter)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("") }
            )

        }


        if (isLoading) {
            CircularProgressIndicator()
            Text("Cargando objetos...")
        } else if (isInventory){
            inventoryItems.forEach { detail ->
                if (detail.quantity > 0 ){
                    ItemSummaryComponent(
                        item = detail.item,
                        onClick = { itemViewModel.destroyItem(currentCharacter!!, detail.item) } ,
                        quantity = detail.quantity,

                    )
                    HorizontalDivider()
                }
            }
        } else {
            shopItems.forEach { item ->

                ItemSummaryComponent(
                    item = item,
                    isSelling = true,
                    onClick = {
                        itemViewModel.addItemToCharacter(item)
                        if (currentCharacter!!.gold >= item.goldValue) {
                            goldText = (currentCharacter!!.gold - item.goldValue).toString()
                        }},
                )
                HorizontalDivider()

            }
        }





    }
}


