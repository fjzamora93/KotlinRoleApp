package com.roleapp.character.domain.usecase.character.generateutils

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.RolClass


/** La suma máxima de todos los stats debe ser de 72 */
fun generateStats(character: CharacterEntity): CharacterEntity {

    when (character.rolClass) {
        RolClass.WARRIOR -> {
            character.strength = 15
            character.constitution = 14
            character.dexterity = 13
            character.wisdom = 12
            character.intelligence = 10
            character.charisma = 8
        }
        RolClass.BARD -> {
            character.charisma = 15
            character.dexterity = 14
            character.intelligence = 13
            character.wisdom = 12
            character.constitution = 10
            character.strength = 8
        }
        RolClass.ROGUE -> {
            character.dexterity = 15
            character.intelligence = 14
            character.charisma = 13
            character.constitution = 12
            character.strength = 10
            character.wisdom = 8
        }
        RolClass.EXPLORER -> {
            character.dexterity = 15
            character.constitution = 14
            character.wisdom = 13
            character.intelligence = 12
            character.strength = 10
            character.charisma = 8
        }
        RolClass.CLERIC -> {
            character.wisdom = 15
            character.constitution = 14
            character.charisma = 13
            character.strength = 12
            character.dexterity = 10
            character.intelligence = 8
        }
        RolClass.PALADIN -> {
            character.strength = 15
            character.charisma = 14
            character.constitution = 13
            character.wisdom = 12
            character.dexterity = 10
            character.intelligence = 8
        }

        RolClass.WIZARD -> {
            character.intelligence = 15
            character.dexterity = 14
            character.wisdom = 13
            character.constitution = 12
            character.charisma = 10
            character.strength = 8
        }
        RolClass.DRUID -> {
            character.wisdom = 15
            character.intelligence = 14
            character.constitution = 13
            character.dexterity = 12
            character.strength = 10
            character.charisma = 8
        }

        RolClass.WARLOCK -> {
            character.charisma = 15
            character.wisdom = 14
            character.constitution = 13
            character.intelligence = 12
            character.dexterity = 10
            character.strength = 8
        }
        RolClass.BARBARIAN -> {
            character.strength = 15
            character.constitution = 14
            character.dexterity = 13
            character.wisdom = 12
            character.charisma = 10
            character.intelligence = 8
        }
        else -> {
            character.strength = 11
            character.constitution = 11
            character.dexterity = 11
            character.intelligence = 11
            character.wisdom = 11
            character.charisma = 11
        }
    }
    return character
}
