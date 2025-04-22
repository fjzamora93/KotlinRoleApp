package com.unir.roleapp.character.data.model.local

import androidx.compose.material.icons.Icons
import com.unir.roleapp.R


enum class StatName {
    NONE, COMBAT, CONSTITUTION, STRENGTH, DEXTERITY, INTELLIGENCE, WISDOM, CHARISMA, ARMOR, INITIATIVE,
    POWER, SIZE, SANITY, CURRENT_SANITY, SPEED, CURRENT_HP, CURRENT_AP, LEVEL;

    companion object {
        /** Devuelve la lista de categorías en String*/
        fun getListOf(): List<String> {
            return StatName.entries.map { statName -> getString(statName) }
        }

        /**Recibe un el enum y devuelve un string.*/
        fun getString(statName: StatName): String {
            return when (statName) {
                COMBAT -> "Combate"
                CONSTITUTION -> "Constitución"
                STRENGTH -> "Fuerza"
                DEXTERITY -> "Destreza"
                INTELLIGENCE -> "Inteligencia"
                WISDOM -> "Sabiduría"
                CHARISMA -> "Carisma"
                ARMOR -> "Armadura"
                INITIATIVE -> "Iniciativa"
                CURRENT_AP -> "Energía"
                CURRENT_HP -> "Vida"
                else -> ""
            }
        }

        /**Recibe un string y devuelve el enum correspondiente.*/
        fun getStatName(statName: String): StatName {
            val normalizedCategory = statName.trim().uppercase()
            return when {
                normalizedCategory.contains("CONST") -> StatName.CONSTITUTION
                normalizedCategory.contains("STR") -> StatName.STRENGTH
                normalizedCategory.contains("DEX") -> StatName.DEXTERITY
                normalizedCategory.contains("INT") -> StatName.INTELLIGENCE
                normalizedCategory.contains("WIS") -> StatName.WISDOM
                normalizedCategory.contains("CHA") -> StatName.CHARISMA
                normalizedCategory.contains("ARM") -> StatName.ARMOR
                normalizedCategory.contains("INI") -> StatName.INITIATIVE
                normalizedCategory.contains("COMB") -> StatName.COMBAT

                normalizedCategory.contains("AP") -> StatName.CURRENT_AP
                normalizedCategory.contains("HP") -> StatName.CURRENT_HP
                else -> StatName.NONE
            }
        }

        fun getIcon(statName: StatName): Int {
            return when (statName) {
                StatName.COMBAT         -> R.drawable.weapon_sword
                StatName.CONSTITUTION   -> R.drawable.icon_constitution
                StatName.STRENGTH       -> R.drawable.icon_strength
                StatName.DEXTERITY      -> R.drawable.icon_dexterity
                StatName.INTELLIGENCE   -> R.drawable.icon_intelligence
                StatName.WISDOM         -> R.drawable.icon_wisdom
                StatName.CHARISMA       -> R.drawable.icon_charisma
                StatName.ARMOR          -> R.drawable.armor
                StatName.INITIATIVE     -> R.drawable.initiative_24
                StatName.CURRENT_AP     -> R.drawable.magic
                StatName.CURRENT_HP     -> R.drawable.baseline_heart_broken_24
                else                    -> R.drawable.sharp_swords_24
            }
        }
    }
}
