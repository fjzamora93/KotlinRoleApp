package com.unir.roleapp.character.data.model.local

import com.roleapp.character.data.model.local.Race

enum class ItemCategory {
    CONSUMABLES, WEAPON, EQUIPMENT, COMMON;

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
            }
        }

        /**Recibe un string y devuelve el enum correspondiente.*/
        fun getItemCategory(itemCategoryName: String): ItemCategory {
            return ItemCategory.entries.find { getString(it) == itemCategoryName } ?: COMMON
        }
    }
}
