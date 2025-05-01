package com.unir.roleapp.character.data.model.local


enum class RolClass {
    NULL, WARRIOR, BARD, ROGUE, EXPLORER, CLERIC, PALADIN, WIZARD, DRUID, WARLOCK, BARBARIAN;

    companion object {

        fun getListOf(): List<String>{
            return entries.map { rol -> getString(rol) }
        }

        fun getString(rolClass: RolClass): String {
            return when (rolClass) {
                NULL -> "None"
                WARRIOR -> "Guerrero"
                BARD -> "Bardo"
                ROGUE -> "Pícaro"
                EXPLORER -> "Explorador"
                CLERIC -> "Clérigo"
                PALADIN -> "Paladín"
                WIZARD -> "Mago"
                DRUID -> "Druida"
                WARLOCK -> "Brujo"
                BARBARIAN -> "Bárbaro"
            }
        }

        fun getClass(roleName: String): RolClass {
            return entries.find { getString(it) == roleName } ?: NULL
        }
    }
}