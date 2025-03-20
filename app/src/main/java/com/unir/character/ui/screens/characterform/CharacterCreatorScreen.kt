package com.unir.character.ui.screens.characterform


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.unir.character.data.model.local.Race
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.RolClass
import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.di.LocalAuthViewModel
import com.navigation.ScreensRoutes
import com.ui.components.BackButton
import com.ui.layout.MainLayout
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.auth.viewmodels.UserState
import com.unir.auth.viewmodels.AuthViewModel
import com.ui.theme.MedievalColours

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
    authViewModel: AuthViewModel = LocalAuthViewModel.current,
    isEditing: Boolean = false,
    onEditComplete: (Boolean) -> Unit = { }
){
    val userState by authViewModel.userState.collectAsState()

    val editableCharacter by characterViewModel.selectedCharacter.collectAsState()

    var name by remember { mutableStateOf("") }
    var rolClass by remember { mutableStateOf(RolClass.WARRIOR) }
    var race by remember { mutableStateOf(Race.HUMAN) }
    var size by remember { mutableIntStateOf(11) }
    var age by remember { mutableIntStateOf(18) }

    LaunchedEffect(editableCharacter) {
        if (isEditing && editableCharacter != null) {
            println("TENEMOS UN PESONAJE EXISTENTE CON ID" + editableCharacter!!.id)
            name = editableCharacter!!.name
            rolClass = editableCharacter!!.rolClass
            race = editableCharacter!!.race
            size = editableCharacter!!.armor
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

        val characterToSave: CharacterEntity
        if (isEditing && editableCharacter != null) {
            characterToSave = editableCharacter!!.copy()
        } else {
            val userId = userState.let { (it as UserState.Success).user.id }
            characterToSave = CharacterEntity(
                updatedAt = System.currentTimeMillis(),
                userId = userId!!
            )
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
fun InsertCharacterButton(
    newCharacter: CharacterEntity,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    onEditComplete: (Boolean) -> Unit = { }
) {
    val navigationViewModel = LocalNavigationViewModel.current
    var isNavigating by remember { mutableStateOf(false) }
    BackButton()
    Button(
        onClick = {
            characterViewModel.updateCharacter(newCharacter)
            isNavigating = true
            onEditComplete(false)
        }
    ) {
        Text("Guardar")
    }

    // Utiliza LaunchedEffect para esperar a que selectedCharacter se actualice  y haya insertado en la base de datos
    LaunchedEffect(isNavigating, newCharacter) {
        if (isNavigating ) {
            navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(newCharacter.id))
            isNavigating = false
        }
    }
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
