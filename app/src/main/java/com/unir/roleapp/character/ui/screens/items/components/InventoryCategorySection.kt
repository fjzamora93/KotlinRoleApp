package com.unir.roleapp.character.ui.screens.items.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.roleapp.character.data.model.local.CharacterItemDetail
import com.roleapp.character.data.model.local.Item


@Composable
fun InventoryByCategorySection(
    filteredItems: List<CharacterItemDetail>,
    filter: String,
    onClick : (Item) -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    val backgroundColor = MaterialTheme.colorScheme.primary
    val contentColor = MaterialTheme.colorScheme.onPrimary

    // Acabar con el componente en caso de que no haya nada que mostrar
    if (filteredItems.isEmpty()) return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$filter",
                color = contentColor,
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (visible) "Ocultar" else "Mostrar",
                    tint = contentColor
                )
            }
        }

        AnimatedVisibility(visible = visible) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                filteredItems.forEach { item ->
                    if (item.quantity > 0 ) {
                        ItemSummaryComponent(
                            item = item.item,
                            onClick = { onClick(item.item) },
                            quantity = item.quantity,
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}