package com.unir.roleapp.character.ui.screens.items.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.CharacterItemDetail
import com.roleapp.character.data.model.local.Item
import com.roleapp.character.ui.screens.common.DropDownText
import com.roleapp.character.ui.screens.common.NumberRangeDropDown
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.ui.components.common.DefaultColumn
import com.unir.roleapp.R
import com.unir.roleapp.character.data.model.local.ItemCategory
import com.unir.roleapp.character.data.model.local.StatName


@Composable
fun ItemForm(
    itemViewModel: ItemViewModel = hiltViewModel(),
    characterViewModel: CharacterViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSave: (Item) -> Unit
) {
    var isEditingImage by remember { mutableStateOf(false) }
    var item = Item(
        id = 0,
        gameSession = 0,
        name = "",
        description = "",
        imgUrl = "",
        goldValue = 0,
        category = ItemCategory.WEAPON,
        dice = 0,
        diceAmount = 0,
        statType = StatName.COMBAT,
        statValue = 0
    )
    var goldValueText by remember { mutableStateOf(item.goldValue.toString()) }

    DefaultColumn {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { isEditingImage = true }
        ) {
            IconButton(onClick = { isEditingImage = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_portrait_24),
                    contentDescription = "Seleccionar Imagen",
                    modifier = Modifier.size(240.dp)
                )
            }

            Text(
                "Subir Imagen",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )

            if (isEditingImage) {
                Dialog(onDismissRequest = { isEditingImage = false }) {
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(16.dp))

        TextField(
            value = item.name,
            onValueChange = { item = item.copy(name = it) },
            label = { Text("Nombre", color = colorResource(id = R.color.gray)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = item.description,
            onValueChange = { item = item.copy(description = it) },
            label = { Text("Descripción", color = colorResource(id = R.color.gray)) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,
            singleLine = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ORO
        TextField(
            value = goldValueText,
            onValueChange = { newText ->
                goldValueText = newText
                newText.toIntOrNull()?.let { item = item.copy(goldValue = it) }
            },
            label = { Text("Oro", color = colorResource(id = R.color.gray)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Categoría (Dropdown personalizado según tu enum)
        DropDownText(
            options = ItemCategory.getListOf(),
            selectedOption = ItemCategory.getString(item.category),
            label = "Categoría",
            onValueChange = { item = item.copy(category = ItemCategory.getItemCategory(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            NumberRangeDropDown(
                validRange = 4..20,
                selectedValue = item.dice,
                modifier = Modifier.weight(1f),
                label = "Tipo de Dado",
                onValueChange = { item = item.copy(dice = it) }
            )

            Spacer(modifier = Modifier.width(16.dp))

            NumberRangeDropDown(
                validRange = 1..10,
                selectedValue = item.diceAmount,
                modifier = Modifier.weight(1f),
                label = "Cantidad de Dados",
                onValueChange = { item = item.copy(diceAmount = it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DropDownText(
            options = StatName.getListOf(),
            selectedOption = StatName.getString(item.statType),
            label = "Atributo",
            onValueChange = { item = item.copy(statType = StatName.getStatName(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        NumberRangeDropDown(
            validRange = -10..10,
            selectedValue = item.statValue,
            modifier = Modifier.fillMaxWidth(),
            label = "Modificador del Atributo",
            onValueChange = { item = item.copy(statValue = it) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                itemViewModel.addItemToCharacter(item)
                onDismiss()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Guardar")
        }
    }

}