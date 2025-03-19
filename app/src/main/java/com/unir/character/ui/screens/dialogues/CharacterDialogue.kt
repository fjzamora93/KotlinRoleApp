package com.unir.character.ui.screens.dialogues

import androidx.compose.runtime.Composable


sealed class CharacterDialog {
    object Armour : CharacterDialog()
    object Stats : CharacterDialog()
    object Skills : CharacterDialog()
    object Initiative : CharacterDialog()
    object Inventory : CharacterDialog()
    data class Custom(val message: String) : CharacterDialog()
}
