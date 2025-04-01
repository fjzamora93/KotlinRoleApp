package com.roleapp.character.ui.screens.common

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownText(
    modifier: Modifier = Modifier,
    options: List<String>,
    selectedOption: String,
    onValueChange: (String) -> Unit,
    label: String = "",
) {
    // Estado para controlar si el menú está expandido o no
    var expanded by remember { mutableStateOf(false) }

    // ExposedDropdownMenuBox es el contenedor del dropdown
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier
    ) {
        // TextField que muestra la opción seleccionada
        TextField(
            value = selectedOption, // Usar el valor que viene desde fuera
            onValueChange = {}, // No se permite editar manualmente
            readOnly = true, // Hacer el TextField de solo lectura
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            label = { Text(text = label) },
            modifier = Modifier.menuAnchor()
        )

        // Menú desplegable
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onValueChange(option) // Notificar al componente padre sobre el cambio
                        expanded = false // Cerrar el menú
                    }
                )
            }
        }
    }
}