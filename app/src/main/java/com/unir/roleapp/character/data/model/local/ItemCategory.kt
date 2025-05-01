package com.unir.roleapp.character.data.model.local

import androidx.compose.ui.text.toUpperCase
import com.unir.roleapp.character.data.model.local.Race
import java.util.Locale

enum class ItemCategory {
    ALL, CONSUMABLES, WEAPON, EQUIPMENT, COMMON;

    companion object {
        /** Devuelve la lista de categorías en String*/
        fun getListOf(): List<String> {
            return ItemCategory.entries.map { itemCategory -> getString(itemCategory) }
        }

        /**Recibe un el enum y devuelve un string.*/
        fun getString(itemCategory: ItemCategory): String {
            return when (itemCategory) {
                CONSUMABLES -> "Consumibles"
                WEAPON -> "Armas"
                EQUIPMENT -> "Equipo"
                COMMON -> "Genérico"
                ALL -> "Todos"
                else -> "Genérico"
            }
        }

        /**Recibe un string y devuelve el enum correspondiente.*/
        fun getItemCategory(itemCategoryName: String): ItemCategory {
            val normalizedCategory = itemCategoryName.trim().uppercase()
            return when {
                normalizedCategory.contains("CONSUMIBLE") || normalizedCategory.contains("CONSUMABLES") || normalizedCategory.contains("CONSUMABLE") -> ItemCategory.CONSUMABLES
                normalizedCategory.contains("ARMA") || normalizedCategory.contains("WEAPON") -> ItemCategory.WEAPON
                normalizedCategory.contains("EQUIPO") || normalizedCategory.contains("EQUIPMENT") -> ItemCategory.EQUIPMENT
                normalizedCategory.contains("GENÉRICO") || normalizedCategory.contains("COMÚN") || normalizedCategory.contains("COMMON") -> ItemCategory.COMMON
                normalizedCategory.contains("TODOS") || normalizedCategory.contains("ALL") -> ItemCategory.ALL
                else -> {
                    println("WARNING: Unknown category: $itemCategoryName") // Add a warning log
                    ItemCategory.COMMON
                }
            }
        }


    }
}
