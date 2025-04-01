package com.roleapp.character.data.model.local

enum class Race {
    HUMAN, ELF, DWARF, HALFLING, DRAGONBORN, ORC, OTHER;

    companion object {
        fun getListOf(): List<String> {
            return entries.map { race -> getString(race) }
        }


        fun getString(race: Race): String {
            return when (race) {
                HUMAN -> "Humano"
                ELF -> "Elfo"
                DWARF -> "Enano"
                HALFLING -> "Mediano"
                DRAGONBORN -> "Dragonborn"
                ORC -> "Orco"
                OTHER -> "Otro"
            }
        }

        fun getRace(raceName: String): Race {
            return entries.find { getString(it) == raceName } ?: OTHER
        }
    }
}
