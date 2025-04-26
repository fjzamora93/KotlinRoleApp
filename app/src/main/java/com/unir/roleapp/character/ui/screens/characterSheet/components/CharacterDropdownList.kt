package com.unir.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.dp
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.ui.screens.characterSheet.components.CharacterPortrait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDropDown(
    modifier: Modifier = Modifier,
    options: List<CharacterEntity>,
    selectedOption: CharacterEntity?,
    onValueChange: (CharacterEntity) -> Unit,
    label: String = "",
) {
    var expanded by remember { mutableStateOf(false) }
    var currentText by remember(selectedOption) {
        mutableStateOf(selectedOption?.name ?: "")
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = currentText,
            onValueChange = {}, // No editable
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            label = { Text(text = label) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { character ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CharacterPortrait(
                                size = 48,
                                character = character,
                                onClick = {} // No se hace nada al clickear la imagen en dropdown
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = character.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    },
                    onClick = {
                        onValueChange(character)
                        currentText = character.name // 👈 actualiza el texto mostrado
                        expanded = false
                    }
                )
            }
        }
    }
}
