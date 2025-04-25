package com.unir.roleapp.adventure.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.unir.roleapp.adventure.domain.model.Character

/**
 * Muestra una lista de personajes con checkbox.
 */
@Composable
fun CharacterSelectionList(
    characters: List<Character>,
    selectedIds: List<Long>,
    onSelect: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(characters) { char ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
            ) {
                Checkbox(
                    checked = selectedIds.contains(char.id),
                    onCheckedChange = { onSelect(char.id) }
                )
                Spacer(Modifier.width(8.dp))
                Text(text = char.name)
            }
        }
    }
}