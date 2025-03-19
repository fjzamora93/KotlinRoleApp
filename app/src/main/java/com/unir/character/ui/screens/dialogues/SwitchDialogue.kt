package com.unir.character.ui.screens.dialogues

import androidx.compose.runtime.Composable


@Composable
fun SwitchDialogue(
    activeDialog: CharacterDialog,
    onDismiss: (CharacterDialog) -> Unit
) {
    val dialogText = dialogTexts[activeDialog] ?: (activeDialog as? CharacterDialog.Custom)?.message

    dialogText?.let { text ->
        FullScreenInfoDialog(
            text = text,
            onDismiss = { onDismiss(activeDialog) }
        )
    }
}