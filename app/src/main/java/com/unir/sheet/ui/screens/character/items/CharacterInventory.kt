package com.unir.sheet.ui.screens.character.items

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.sheet.data.model.Item
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.components.RegularCard
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel
import com.unir.sheet.ui.viewmodels.ItemViewModel
import com.unir.sheet.util.MedievalColours

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
    val currentCharacter by characterViewModel.selectedCharacter.observeAsState()
    val inventoryItems by itemViewModel.itemList.observeAsState()
    val isLoading by itemViewModel.loadingState.observeAsState(false) // Asumiendo que tienes un estado de carga


    itemViewModel.getItemsByCharacterId(currentCharacter!!.id!!)


    // Mostrar mensajes de carga o error
    if (isLoading) {
        Text("Cargando objetos...")
    } else {
        if (inventoryItems != null) {
            Column(
                modifier = modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CurrentGold()
                inventoryItems!!.forEach { item ->
                    InventoryItemCard(item = item)
                }
            }
        } else {
            Text("No se encontraron objetos")
        }
    }
}



@Composable
fun InventoryItemCard(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    itemViewModel: ItemViewModel = hiltViewModel(),
    item: Item,
    modifier: Modifier = Modifier
) {
    RegularCard() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Daño: ${item.dice}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Precio: ${item.goldValue}",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = {
                val currentCharacter = characterViewModel.selectedCharacter.value
                if (currentCharacter != null) {
                    itemViewModel.sellItem(currentCharacter, item)
                }
            }) {
                Text(text = "vender")
            }

            Button(onClick = {
                val currentCharacter = characterViewModel.selectedCharacter.value
                if (currentCharacter != null) {
                    itemViewModel.removeItemFromCharacter(currentCharacter, item)
                }
            }) {
                Text(text = "consumir")
            }
        }
    }
}

@Composable
fun CurrentGold(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current
){
    val selectedCharacter by characterViewModel.selectedCharacter.observeAsState()
    var goldText by remember { mutableStateOf(selectedCharacter?.gold?.toString() ?: "0") }
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ){
        Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = "", tint = MedievalColours.Gold, modifier = Modifier.size(40.dp))

        TextField(
            value = selectedCharacter!!.gold.toString(),
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
