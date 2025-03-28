package com.unir.character.ui.screens.characterform.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ui.components.common.DefaultColumn
import com.unir.character.ui.screens.common.DropDownText
import com.unir.character.ui.screens.common.dialogues.PersonalityTestOptions

data class PersonalityTestForm(
    var combatStyle: String = "",
    var sociability: String= "",
    var hobbies: String= "",
    var afraid: String = "",
    var proeficiency: String = "",
)


@Composable
fun PersonalityTest(
    onValueChange: (PersonalityTestForm) -> Unit,
) {
    // Estado para almacenar el formulario completo
    var form by remember {
        mutableStateOf(
            PersonalityTestForm()
        )
    }


    DefaultColumn {
        // Pregunta 1: Estilo de combate
        Text("¿Qué estilo de combate tiene tu personaje?")
        DropDownText(
            options = PersonalityTestOptions.combatOptions,
            selectedOption = form.combatStyle,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(combatStyle = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Estilo de combate",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )

        // Pregunta 2: Sociabilidad
        Spacer(modifier = Modifier.height(16.dp))
        Text("A la hora de relacionarse con otros personajes...")
        DropDownText(
            options = PersonalityTestOptions.sociabilityOptions,
            selectedOption = form.sociability,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(sociability = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Sociabilidad",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )

        // Pregunta 3: Hobbies
        Spacer(modifier = Modifier.height(16.dp))
        Text("¿Cuáles son los hobbies de tu personaje?")
        DropDownText(
            options = PersonalityTestOptions.hobbiesOptions,
            selectedOption = form.hobbies,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(hobbies = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Aficiones",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )

        // Pregunta 4: Miedos
        Spacer(modifier = Modifier.height(16.dp))
        Text("¿A qué le tiene miedo tu personaje?")
        DropDownText(
            options = PersonalityTestOptions.afraidOptions,
            selectedOption = form.afraid,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(afraid = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Miedos",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )

        // Pregunta 5: Talentos
        Spacer(modifier = Modifier.height(16.dp))
        Text("¿Cuál es la especialidad de tu personaje?")
        DropDownText(
            options = PersonalityTestOptions.proeficiencyOptions,
            selectedOption = form.proeficiency,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(proeficiency = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Talentos",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
    }
}
