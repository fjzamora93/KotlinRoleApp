package com.roleapp.character.ui.screens.characterform.components

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.roleapp.core.ui.components.common.DefaultColumn
import com.roleapp.character.ui.screens.common.DropDownText
import com.roleapp.character.ui.screens.common.dialogues.PersonalityTestOptions
import com.unir.roleapp.R

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


    Column {
        // Pregunta 1: Estilo de combate
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
        DropDownText(
            options = PersonalityTestOptions.sociabilityOptions,
            selectedOption = form.sociability,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(sociability = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Habilidades sociales",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )

        // Pregunta 3: Hobbies
        Spacer(modifier = Modifier.height(16.dp))
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
        DropDownText(
            options = PersonalityTestOptions.afraidOptions,
            selectedOption = form.afraid,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(afraid = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Defectos y fobias",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )

        // Pregunta 5: Talentos
        Spacer(modifier = Modifier.height(16.dp))
        DropDownText(
            options = PersonalityTestOptions.proeficiencyOptions,
            selectedOption = form.proeficiency,
            onValueChange = { selectedOption ->
                val updatedForm = form.copy(proeficiency = selectedOption)
                form = updatedForm
                onValueChange(updatedForm)
            },
            label = "Especialidad y talentos",
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
    }
}
