package com.unir.roleapp.character.ui.screens.common.dialogues


sealed class CharacterDialog {
    object Armour : CharacterDialog()
    object Stats : CharacterDialog()
    object Skills : CharacterDialog()
    object Initiative : CharacterDialog()
    object Inventory : CharacterDialog()
    object Spell: CharacterDialog()
    data class Custom(val message: String) : CharacterDialog()
}
