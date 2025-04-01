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
fun NumberRangeDropDown(
    validRange: IntRange, // El rango de números a mostrar
    selectedValue: Int, // Valor seleccionado
    onValueChange: (Int) -> Unit,
    modifier : Modifier = Modifier,
    label: String = "Número"
) {
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // TextField que muestra el valor seleccionado
        TextField(
            value = selectedValue.toString(), // Mostrar el valor seleccionado
            onValueChange = {}, // No permitir edición manual
            readOnly = true, // Hacer que sea solo de lectura
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
            validRange.forEach { value ->
                DropdownMenuItem(
                    text = { Text(text = value.toString()) },
                    onClick = {
                        onValueChange(value) // Notificar al componente padre sobre el cambio
                        expanded = false // Cerrar el menú
                    }
                )
            }
        }
    }
}
