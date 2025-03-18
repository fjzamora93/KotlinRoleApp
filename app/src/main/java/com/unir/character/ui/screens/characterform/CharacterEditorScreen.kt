package com.unir.character.ui.screens.characterform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes
import com.ui.layout.MainLayout
import com.unir.character.data.model.local.Race
import com.unir.character.data.model.local.RolClass
import com.unir.character.viewmodels.CharacterViewModel


@Composable
fun CharacterEditorScreen(){
    MainLayout(){
        Column{
            CharacterEditForm()
        }
    }
}

/**TODO: PENDIENTE DE AÑADIR AL FORMULARIO
 * - Imagen
 * - Nivel
 * - Stats
 * - Skills
 * - Armadura
 * - Iniciativa
 * - Puntos de golpe.
 * - Puntos de magia
 *
 * */
@Composable
fun CharacterEditForm(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current
) {
    val editableCharacter by characterViewModel.selectedCharacter.collectAsState()

    // Crear una copia mutable del personaje al iniciar el formulario
    var characterToUpdate by remember(editableCharacter) {
        mutableStateOf(editableCharacter?.copy())
    }

    characterToUpdate?.let { character ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = character.name,
                onValueChange = { newName ->
                    characterToUpdate = character.copy(name = newName)
                },
                label = { Text("Nombre") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Clase", style = MaterialTheme.typography.labelMedium)
            DropDown(
                options = RolClass.getListOf(),
                selectedOption = RolClass.getString(character.rolClass), // Mostrar la opción actual
                onValueChange = { selectedOption ->
                    characterToUpdate = character.copy(rolClass = RolClass.getClass(selectedOption))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Raza", style = MaterialTheme.typography.labelMedium)
            DropDown(
                options = Race.getListOf(),
                selectedOption = Race.getString(character.race), // Mostrar la opción actual
                onValueChange = { selectedOption ->
                    characterToUpdate = character.copy(race = Race.getRace(selectedOption))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            NumberPicker(
                state = remember { mutableIntStateOf(character.charisma) },
                onStateChanged = {result ->
                    characterToUpdate = character.copy(charisma = result)
                    println(result)
                }
            )

            Button(
                onClick = {
                    characterToUpdate?.let { updatedCharacter ->
                        characterViewModel.updateCharacter(updatedCharacter)
                        navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(updatedCharacter.id))
                    }
                }
            ) {
                Text("Guardar")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(
    options: List<String>,
    selectedOption: String, // Valor seleccionado que viene desde fuera
    onValueChange: (String) -> Unit // Función para notificar cambios
) {
    // Estado para controlar si el menú está expandido o no
    var expanded by remember { mutableStateOf(false) }

    // ExposedDropdownMenuBox es el contenedor del dropdown
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // TextField que muestra la opción seleccionada
        TextField(
            value = selectedOption, // Usar el valor que viene desde fuera
            onValueChange = {}, // No se permite editar manualmente
            readOnly = true, // Hacer el TextField de solo lectura
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
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