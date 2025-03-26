package com.unir.character.ui.screens.common.dialogues

import androidx.compose.runtime.Composable


/** Composable para mostrar un diálogo de información en pantalla completa.
 *
 * REcibe como parámetro el texto del diálogo y una acción de cierre.
 * */
@Composable
fun SwitchDialogue(
    activeDialog: CharacterDialog,
    onDismiss: (CharacterDialog) -> Unit,
) {
    val dialogText = dialogTexts[activeDialog] ?: (activeDialog as? CharacterDialog.Custom)?.message

    dialogText?.let { text ->
        FullScreenInfoDialog(
            text = text,
            onDismiss = { onDismiss(activeDialog) }
        )
    }
}