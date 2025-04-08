package com.roleapp.character.ui.screens.items.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel

import com.roleapp.character.ui.screens.common.layout.CharacterLayout
import com.roleapp.character.ui.screens.items.ShopComponent

import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.unir.roleapp.character.ui.screens.items.components.CurrentGoldComponent
import com.unir.roleapp.character.ui.screens.common.RectangularButton
import com.unir.roleapp.character.ui.screens.items.components.InventoryItemCard
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

    CharacterLayout { onClickDrawer ->
        selectedCharacter?.let { character ->
            CharacterInventoryBody()
        } ?: CrossSwordsAnimation()
    }
}


@Composable
fun CharacterInventoryBody(
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
    itemViewModel: ItemViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val inventoryItems by itemViewModel.itemsByCharacter.collectAsState()
    val isLoading by itemViewModel.loadingState.collectAsState(false)

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

    Column(
        modifier = modifier.padding(16.dp),
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

        CurrentGoldComponent()


        if (isLoading) {
            CircularProgressIndicator()
            Text("Cargando objetos...")
        } else if (isInventory){
            inventoryItems.forEach { details ->
                if (details.quantity > 0 ){
                    InventoryItemCard(item = details.item , quantity = details.quantity)
                }
            }
        } else {
            ShopComponent()
        }

    }
}


