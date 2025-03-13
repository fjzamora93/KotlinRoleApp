package com.unir.character.ui.screens.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.di.LocalCharacterViewModel

import com.unir.character.data.model.local.Item
import com.unir.character.ui.screens.components.BackButton
import com.unir.character.ui.screens.components.RegularCard
import com.unir.character.ui.screens.layout.MainLayout

import com.unir.character.viewmodels.CharacterViewModel
import com.unir.character.viewmodels.ItemViewModel
import com.util.MedievalColours

@Composable
fun CharacterInventoryScreen(){
    MainLayout(){
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            CharacterInventoryBody()
            BackButton()
        }
    }
}


@Composable
fun CharacterInventoryBody(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    itemViewModel: ItemViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()
    val inventoryItems by itemViewModel.itemsByCharacter.collectAsState()
    val isLoading by itemViewModel.loadingState.collectAsState(false)

    LaunchedEffect(currentCharacter?.id) {
        currentCharacter?.id?.let { id ->
            itemViewModel.getItemsByCharacterId(id)
        }
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ){
        CurrentGold()
        if (isLoading) {
            CircularProgressIndicator()
            Text("Cargando objetos...")
        } else {
            inventoryItems.forEach { details ->
                if (details.quantity > 0 ){
                    InventoryItemCard(item = details.item , quantity = details.quantity)
                }
            }
        }
    }

}



@Composable
fun InventoryItemCard(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
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

@Composable
fun CurrentGold(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current
){
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    var goldText by remember { mutableStateOf(selectedCharacter?.gold?.toString() ?: "0") }
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = "", tint = MedievalColours.Gold, modifier = Modifier.size(40.dp))

        TextField(
            value = goldText,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    goldText = newValue
                    val currentCharacter = characterViewModel.selectedCharacter.value
                    currentCharacter!!.gold = newValue.toIntOrNull() ?: 0
                    characterViewModel.updateCharacter(currentCharacter)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Cantidad de oro") }
        )

    }



}
