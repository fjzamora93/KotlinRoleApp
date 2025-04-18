package com.unir.roleapp.character.domain.usecase.character.generateutils

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.Race
import com.roleapp.character.data.model.local.RolClass

fun calculateCharacterHealth(character: CharacterEntity) : Int{

    // Calculamos estadística base en función de la clase
    val baseHealth = when (character.rolClass) {
        RolClass.WARRIOR, RolClass.BARBARIAN -> 12
        RolClass.PALADIN,  RolClass.EXPLORER, RolClass.CLERIC -> 10
        RolClass.ROGUE,  RolClass.DRUID, RolClass.WARLOCK -> 8
        RolClass.WIZARD, RolClass.BARD -> 6
        else -> 6
    }

    // Calculamos el bonus por constitución
    val constitutionBonus = (character.constitution - 10) / 2

    // Calculamos el bonus por raza
    val raceBonus = when (character.race) {
        Race.DWARF, Race.ORC -> 2
        Race.DRAGONBORN, Race.HUMAN -> 1
        Race.ELF, Race.HALFLING -> -1
        else -> 0
    }

    // Calculamos el bonus por nivel
    val levelBonus = character._level  * 2


    val result = baseHealth + constitutionBonus + raceBonus + levelBonus

    return result
}



fun calculateCharacterActionPoints(character: CharacterEntity) : Int{

    // Calculamos estadística base en función de la clase
    val baseAp = when (character.rolClass) {
        RolClass.WIZARD,  RolClass.WARLOCK -> 6
        RolClass.CLERIC, RolClass.DRUID, RolClass.BARD -> 5
        RolClass.EXPLORER, RolClass.ROGUE -> 4
        RolClass.WARRIOR, RolClass.PALADIN, RolClass.BARBARIAN -> 3
        else -> 3
    }

    // Calculamos el bonus por constitución
    val wisdomBonus = (character.wisdom - 10) / 2
    val intelligenceBonus = (character.intelligence - 10) / 2

    // Calculamos el bonus por raza
    val raceBonus = when (character.race) {
        Race.DWARF, Race.ORC -> -2
        Race.DRAGONBORN, Race.HUMAN -> 0
        Race.ELF, Race.HALFLING -> 1
        else -> 0
    }

    // Calculamos el bonus por nivel
    val levelBonus = character._level  * 1


    val result = baseAp + wisdomBonus + intelligenceBonus + raceBonus + levelBonus

    return result
}