package com.unir.character.ui.screens.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.di.LocalCharacterViewModel
import com.di.LocalNavigationViewModel
import com.navigation.NavigationViewModel
import com.navigation.ScreensRoutes
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Race
import com.unir.character.data.model.local.RolClass
import com.unir.character.ui.screens.characterform.StatSectionForm
import com.unir.character.ui.screens.common.DropDownText
import com.unir.character.ui.screens.common.NumberRangeDropDown
import com.unir.character.ui.screens.dialogues.PersonalityTestOptions
import com.unir.character.viewmodels.CharacterViewModel
import com.unir.character.viewmodels.SkillViewModel

data class PersonalityTestForm(
    val combatStyle: String,
    val sociability: String,
    val hobbies: String,
    val afraid: String,
    val proeficiency: String
)




@Composable
fun PersonalityTestFormScreen(
    viewModel: SkillViewModel = hiltViewModel(),
    character: CharacterEntity
) {
    // Estado para almacenar las respuestas
    var combatStyle by remember { mutableStateOf("") }
    var sociability by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf("") }
    var afraid by remember { mutableStateOf("") }
    var proeficiency by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Pregunta 1: Estilo de combate
        Text("¿Qué estilo de combate tiene tu personaje?")
        DropDownText(
            options = PersonalityTestOptions.combatOptions,
            selectedOption = combatStyle,
            onValueChange = { selectedOption ->
                combatStyle = selectedOption
            },
            label = "Estilo de combate",
            modifier = Modifier
                .weight(1.0f)
                .padding(end = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pregunta 2: Sociabilidad
        Text("A la hora de relacionarse con otros personajes...")
        DropDownText(
            options = PersonalityTestOptions.sociabilityOptions,
            selectedOption = sociability,
            onValueChange = { selectedOption ->
                sociability = selectedOption
            },
            label = "Sociabilidad",
            modifier = Modifier
                .weight(1.0f)
                .padding(end = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para enviar el formulario
        Button(
            onClick = {
                // Crear el objeto con las respuestas y enviarlo al ViewModel
                val formData = PersonalityTestForm(
                    combatStyle, sociability, hobbies, afraid, proeficiency
                )
                viewModel.submitPersonalityTest(character, formData)
            }
        ) {
            Text("Enviar respuestas")
        }
    }
}


