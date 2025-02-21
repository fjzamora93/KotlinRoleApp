package com.unir.sheet.ui.screens.character


import androidx.compose.foundation.border
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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

import com.unir.sheet.data.model.Gender
import com.unir.sheet.data.model.Race
import com.unir.sheet.data.model.Range
import com.unir.sheet.data.model.RolCharacter
import com.unir.sheet.data.model.RolClass
import com.unir.sheet.di.LocalCharacterViewModel
import com.unir.sheet.di.LocalNavigationViewModel
import com.unir.sheet.ui.navigation.ScreensRoutes
import com.unir.sheet.ui.screens.components.BackButton
import com.unir.sheet.ui.screens.layout.MainLayout
import com.unir.sheet.ui.viewmodels.CharacterViewModel
import com.unir.sheet.util.MedievalColours

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
    onEditComplete: (Boolean) -> Unit = { }
) {
    val navigationViewModel = LocalNavigationViewModel.current
    val selectedCharacter by characterViewModel.selectedCharacter.observeAsState()
    var isNavigating by remember { mutableStateOf(false) }
    BackButton()
    Button(
        onClick = {
            if (newCharacter.id != null){
                println("Actualizando personaje ${newCharacter.id}")
                characterViewModel.updateCharacter(newCharacter)
            } else {
                println("INsertando nuevo personaje ${newCharacter.id}")
                characterViewModel.insertCharacter(newCharacter)
            }
            isNavigating = true
            onEditComplete(false)
        }
    ) {
        Text("Guardar")
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


/**
 * El formulario tiene dos modos:
 * 1. Edición / update de un personaje ya existente.
 * 2. Creación de un nuevo personaje.
 *
 * En el caso de que haya una actualización, se evaluará la condición para ir modificando el personaje
 * en tiempo de ejecución (no se espera a que terminen de rellenarse todos los campos, sino que se actualizan al momento).
 *
 * POr el contrario, si es un nuevo personaje será necesario esperar a pulsar el botón de guardar para que se produzca la inserción.
 *
 * TOdo esto lo conseguimos gracias al operador ? que nos permite verificar si existe o si no existe.
 *
 * */
@Composable
fun CharacterCreatorForm(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    isEditing: Boolean = false,
    onEditComplete: (Boolean) -> Unit = { }
){
    val editableCharacter by characterViewModel.selectedCharacter.observeAsState()

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var rolClass by remember { mutableStateOf(RolClass.WARRIOR) }
    var race by remember { mutableStateOf(Race.HUMAN) }
    var size by remember { mutableStateOf(Range.MEDIO) }
    var age by remember { mutableStateOf(18) }

    LaunchedEffect(editableCharacter) {
        if (isEditing && editableCharacter != null) {
            println("TENEMOS UN PESONAJE EXISTENTE CON ID" + editableCharacter!!.id)
            name = editableCharacter!!.name
            gender = editableCharacter!!.gender
            rolClass = editableCharacter!!.rolClass
            race = editableCharacter!!.race
            size = editableCharacter!!.height
            age = editableCharacter!!.age
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
            TextField(
                value = name,
                onValueChange = {
                    newName ->
                    name = newName
                    editableCharacter?.let { character ->
                        characterViewModel.updateCharacter(character.copy(name = newName))
                    }},
                label = { Text("Nombre") }
            )

            RolClassDropdown(
                selectedRolClass = rolClass,
                onRolClassSelected = { newRolClass ->
                    rolClass = newRolClass
                    editableCharacter?.let { character ->
                        characterViewModel.updateCharacter(character.copy(rolClass = newRolClass))
                    }
                })

            RaceDropDown(
                selectedRace = race,
                onRaceSelected = { newRace ->
                    race = newRace
                    editableCharacter?.let { character ->
                        characterViewModel.updateCharacter(character.copy(race = newRace))
                    }
                })
        }

        val characterToSave: RolCharacter

        if (isEditing && editableCharacter != null) {
            characterToSave = editableCharacter!!.copy()
        } else {
            characterToSave = RolCharacter()
        }
        characterToSave.name = name
        characterToSave.rolClass = rolClass
        characterToSave.race = race

        InsertCharacterButton(
            newCharacter = characterToSave,
            onEditComplete = { onEditComplete(false) }
        )

}


@Composable
fun RolClassDropdown(
    selectedRolClass: RolClass,
    onRolClassSelected: (RolClass) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        Text(
            text = selectedRolClass.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { expanded = true }
                .border(1.dp, MedievalColours.LeatherAged, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            RolClass.entries.forEach { rol ->
                DropdownMenuItem(
                    text = { Text(text = rol.name) },
                    onClick = {
                        onRolClassSelected(rol)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun RaceDropDown(
    selectedRace: Race,
    onRaceSelected: (Race) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        Text(
            text = selectedRace.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { expanded = true }
                .border(1.dp, MedievalColours.LeatherAged, shape = MaterialTheme.shapes.medium)
                .padding(16.dp),
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Race.entries.forEach { race ->
                DropdownMenuItem(
                    text = { Text(text = race.name) },
                    onClick = {
                        onRaceSelected(race)
                        expanded = false
                    }
                )
            }
        }
    }
}
