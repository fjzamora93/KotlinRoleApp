package com.roleapp.character.ui.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialogueMenu(
    onDismiss: () -> Unit,
    onFirstOption: () -> Unit,
    onSecondOption: () -> Unit,
    firstLabel: String = "Editar",
    secondLable: String = "Eliminar",
    title: String = "Opciones"
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextButton(
                onClick = {
                    onFirstOption()
                    onDismiss()
                }
            ) {
                Text(firstLabel)
            }

            TextButton(
                onClick = {
                    onSecondOption()
                    onDismiss()
                }
            ) {
                Text(secondLable)
            }

            TextButton(
                onClick = { onDismiss() }
            ) {
                Text("Cancelar")
            }
        }
    }
}
