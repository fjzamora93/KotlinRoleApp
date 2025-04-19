package com.roleapp.character.ui.screens.common.dialogues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


/** Composable para mostrar un diálogo de información en pantalla completa.
 *
 * REcibe como parámetro el texto del diálogo y una acción de cierre.
 *
 * Normalmente, el tipo de CharacterDialogue se va a asignar al hacer click
 * en algún botón. Inmediatamente, como su valor dejará de ser nulo, saltará el diálogo.
 *
 * Por ejemplo:
 *
 * onClick = { activeDialog = CharacterDialog.Spell },
 * activeDialog?.let {
*    SwitchDialogue(
*      activeDialog = it,
*      onDismiss = { activeDialog = null }
*    )
* }
 * * */
@Composable
fun SwitchDialogue(
    activeDialog: CharacterDialog,
    onDismiss: (CharacterDialog) -> Unit,
) {
    val dialogText = dialogTexts[activeDialog] ?: (activeDialog as? CharacterDialog.Custom)?.message

    dialogText?.let { text ->
        Dialog(onDismissRequest = { onDismiss(activeDialog) }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(24.dp)
                    .wrapContentSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Button(
                        onClick = { onDismiss(activeDialog) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            "OK",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}
