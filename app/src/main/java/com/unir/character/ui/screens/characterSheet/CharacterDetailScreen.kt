package com.unir.character.ui.screens.characterSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Fireplace
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.ui.components.navigationbar.NavigationBar
import com.unir.character.ui.screens.character.haracterDetail.StatSection
import com.unir.character.ui.screens.skills.SkillSection
import com.unir.character.viewmodels.CharacterViewModel
import kotlinx.coroutines.launch


@Composable
fun CharacterDetailScreen(
    characterId : Long,
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
){

    characterViewModel.getCharacterById(characterId)
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    CharacterMenu(
        drawerState = drawerState,
        onClose = {
            coroutineScope.launch { drawerState.close() }
        }
    ) {

        Scaffold(
            bottomBar = {
                NavigationBar()
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CharacterHeader(
                    onClickMenu = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )

                // CONTENIDO DEL SCREEN.
                LazyColumn(){
                    item {
                        DetailCharacterBody( onClick = {  coroutineScope.launch { drawerState.open() } } )
                    }
                }
            }
        }
    }
}




@Composable
fun DetailCharacterBody(
    characterViewModel: CharacterViewModel = LocalCharacterViewModel.current,
    navigation: NavigationViewModel = LocalNavigationViewModel.current,
    modifier: Modifier = Modifier.fillMaxWidth().padding(16.dp),
    onClick : () -> Unit
) {
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()

    // Si el personaje no está seleccionado, mostramos un texto vacío o de espera
    if (selectedCharacter == null) {
        Text("Cargando personaje...")
    } else {

        // Puesto que el estado de null puede cambiar, llamamos al let
        selectedCharacter?.let { character ->
            var characterState by remember { mutableStateOf(character) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Armadura (icono a la izquierda, número y texto a la derecha)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = "Armadura", Modifier.size(60.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = "15", fontSize = 20.sp)
                            Text(text = "Armadura", fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Fuego/Hoguera (icono a la izquierda, botón a la derecha)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "Iniciativa", Modifier.size(60.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = "+4", fontSize = 20.sp)
                            Text(text = "Iniciativa", fontSize = 12.sp)
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(2.0f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProgressBar(
                        label = "HP",
                        maxValue = characterState.hp,
                        localValue = characterState.currentHp,
                        onValueChanged = { newHp -> characterState = characterState.copy(currentHp = newHp) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProgressBar(
                        label = "AP",
                        maxValue = characterState.ap,
                        localValue = characterState.currentAp,
                        onValueChanged = { newAp -> characterState = characterState.copy(currentAp = newAp) }
                    )
                }
            }


            MenuBar(
                text = "Hechizos, Inventario, Sesión",
                onClick = { onClick() }
            )

            // CAMPOS NUMÉRICOS Y STATS
            StatSection(
                editableCharacter = character,
                onCharacterChange = {
                    characterViewModel.updateCharacter(it)
                }
            )


            SkillSection(
                editableCharacter = character,
            )
        }
    }
}


@Composable
fun MenuBar(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Icono de cuadraditos (Apps)
        Icon(
            imageVector = Icons.Default.Apps,
            contentDescription = "Menú",
            modifier = Modifier.padding(horizontal = 8.dp),
            tint = MaterialTheme.colorScheme.onSurface
        )

        // Texto
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
