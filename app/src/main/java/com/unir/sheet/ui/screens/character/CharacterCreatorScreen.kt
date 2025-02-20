package com.unir.sheet.ui.screens.character


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.sheet.data.local.model.Gender
import com.unir.sheet.data.local.model.Race
import com.unir.sheet.data.local.model.Range
import com.unir.sheet.data.local.model.RolCharacter
import com.unir.sheet.data.local.model.RolClass
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel

@Composable
fun CharacterCreatorScreen() {
    MainLayout {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)){
            Body()
        }
    }
}


@Composable
fun Body(
    modifier:Modifier = Modifier.fillMaxWidth()
){
    Column(modifier = modifier.fillMaxWidth()){
        CharacterCreatorForm()

    }
}

@Composable
fun InsertCharacterButton(
    newCharacter: RolCharacter,
    characterViewModel: CharacterViewModel = hiltViewModel(),
) {
    val navigationViewModel = LocalNavigationViewModel.current
    val selectedCharacter by characterViewModel.selectedCharacter.observeAsState()
    var isNavigating by remember { mutableStateOf(false) }
    BackButton()
    Button(
        onClick = {
            characterViewModel.insertCharacter(newCharacter)
            isNavigating = true
        }
    ) {
        Text("Insertar y navegar")
    }

    // Utiliza LaunchedEffect para esperar a que selectedCharacter se actualice
    LaunchedEffect(isNavigating, selectedCharacter) {
        // Aseguramos que solo se navega cuando isNavigating es true y selectedCharacter tiene un valor válido. SI no lo hacemos, no nos dejará volver atrás.
        if (isNavigating && selectedCharacter?.id != null) {
            selectedCharacter?.id?.let { idNewCharacter ->
                // Ahora que selectedCharacter tiene una id válida, navegamos
                println("ID del último personaje insertado: $idNewCharacter")
                navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(idNewCharacter))
                isNavigating = false // Después de la navegación, desactivamos el flag
            }
        }
    }
}



@Composable
fun CharacterCreatorForm(){

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var rolClass by remember { mutableStateOf(RolClass.WARRIOR) }
    var race by remember { mutableStateOf(Race.HUMANO) }
    var height by remember { mutableStateOf(Range.MEDIO) }
    var weight by remember { mutableStateOf(Range.MEDIO) }
    var age by remember { mutableStateOf(18) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {

        // Nombre y edad
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                Modifier.weight(3f)
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") }
                )
            }
            Column(
                Modifier.weight(1f)
            ) {
                TextField(
                    value = age.toString(),
                    onValueChange = { age = it.toIntOrNull() ?: age },
                    label = { Text("Edad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        // Gender
        GenericDropdown(
            selectedItem = gender,
            items = Gender.entries.toList(),
            onItemSelected = { gender = it },
            itemToString = { it.toString() }
        )

        // Altura y peso
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                Modifier.weight(1f)
            ){
                DropdownSelector(
                    label = "Altura",
                    selectedValue = height,
                    onValueChange = { height = it },
                    values = Range.values(),
                )
            }
            Column(
                Modifier.weight(1f)
            ){
                DropdownSelector(
                    label = "Peso",
                    selectedValue = weight,
                    onValueChange = { weight = it },
                    values = Range.values(),
                )
            }
        }


        // Raza y clase
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                Modifier.weight(1f)
            ){
                DropdownSelector(
                    label = "Clase",
                    selectedValue = rolClass,
                    onValueChange = { rolClass = it },
                    values = RolClass.entries.toTypedArray(),
                )
            }
            Column(
                Modifier.weight(1f)
            ){
                DropdownSelector(
                    label = "Raza",
                    selectedValue = race,
                    onValueChange = { race = it },
                    values = Race.values(),
                )
            }

        }

        InsertCharacterButton(
            newCharacter = RolCharacter(
                name = name,
                gender = gender,
                rolClass = rolClass,
                race = race,
                height = height,
                weight = weight,
                age = age
            )
        )
    }
}


// DROP DOWN
@Composable
fun <T> DropdownSelector(
    label: String,
    selectedValue: T, // Usamos Range como ejemplo, pero puedes usar cualquier tipo de dato
    onValueChange: (T) -> Unit,
    values: Array<T>, // Array de las opciones a mostrar
) where T : Enum<T> {
    var expanded by remember { mutableStateOf(false) }

    TextField(
        value = selectedValue.name,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        trailingIcon = {
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", modifier = Modifier.clickable { expanded = !expanded })
        },
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        values.forEach { selectionOption ->
            DropdownMenuItem(
                onClick = {
                    onValueChange(selectionOption) // Actualiza el valor seleccionado
                    expanded = false
                },
                text = { Text(selectionOption.name) }
            )
        }
    }
}



@Composable
fun <T> GenericDropdown(
    selectedItem: T,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    itemToString: (T) -> String = { it.toString() }
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        Button(onClick = { expanded = true }) {
            Text(text = itemToString(selectedItem))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(text = itemToString(item)) }, onClick = {
                    onItemSelected(item)
                    expanded = false
                })
            }
        }
    }
}