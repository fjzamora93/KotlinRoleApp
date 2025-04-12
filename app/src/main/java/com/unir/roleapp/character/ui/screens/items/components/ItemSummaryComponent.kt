package com.unir.roleapp.character.ui.screens.items.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.Item
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.roleapp.core.ui.components.common.DefaultColumn
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.core.ui.theme.CustomType


@Composable
fun ItemSummaryComponent(
    item: Item,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    isSelling: Boolean = false,
    quantity: Int = 1,
    onClick: () -> Unit,
) {
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()

    currentCharacter?.let {
        DefaultRow{
            // Columan principal del objeto
            Column(
                modifier = Modifier.padding(8.dp).weight(3f),
            ) {
                // Nombre y gold value alineados con espacio entre ellos
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    Text(
                        text = item.name,
                        style = CustomType.titleMedium
                    )
                    if (isSelling){
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = "    ${item.goldValue} 🪙",
                                style = CustomType.bodySmall
                            )

                        }
                    } else {
                        Text(
                            text = "    x $quantity",
                            style = CustomType.titleMedium
                        )
                    }


                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dados: ${item.diceAmount}d${item.dice}",
                        style = CustomType.bodyMedium
                    )
                    Text(

                        text = "    + ${item.statValue} de ${item.statType}",
                        style = CustomType.bodyMedium
                    )
                }
            }

            Column(
                modifier = Modifier.padding(8.dp).weight(0.5f),
                ){
                IconButton(
                    onClick = { onClick() }
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = if (isSelling) Icons.Filled.AddCircle else Icons.Filled.RemoveCircle,
                        contentDescription = "Más opciones",
                    )
                }
            }


        }
    }
}


