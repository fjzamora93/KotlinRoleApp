package com.unir.roleapp.character.data.model.local



enum class StatName {
    NONE, CONSTITUTION, STRENGTH, DEXTERITY, INTELLIGENCE, WISDOM, CHARISMA, ARMOR, INITIATIVE,
    POWER, SIZE, SANITY, CURRENT_SANITY, SPEED, CURRENT_HP, CURRENT_AP, LEVEL;

    companion object {
        /** Devuelve la lista de categorías en String*/
        fun getListOf(): List<String> {
            return StatName.entries.map { statName -> getString(statName) }
        }

        /**Recibe un el enum y devuelve un string.*/
        fun getString(statName: StatName): String {
            return when (statName) {
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
                normalizedCategory.contains("CONST")  -> StatName.CONSTITUTION
                normalizedCategory.contains("STRE")   -> StatName.STRENGTH
                normalizedCategory.contains("DEX")    -> StatName.DEXTERITY
                normalizedCategory.contains("INT")    -> StatName.INTELLIGENCE
                normalizedCategory.contains("WIS")    -> StatName.WISDOM
                normalizedCategory.contains("CHA")    -> StatName.CHARISMA
                normalizedCategory.contains("ARM")    -> StatName.ARMOR
                normalizedCategory.contains("INI")    -> StatName.INITIATIVE

                normalizedCategory.contains("AP")    -> StatName.CURRENT_AP
                normalizedCategory.contains("HP")    -> StatName.CURRENT_HP
                else -> StatName.NONE
            }
        }


    }
}
