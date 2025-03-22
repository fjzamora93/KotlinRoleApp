package com.unir.character.domain.usecase.character.generateutils

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.data.model.local.RolClass

fun adjustCharacterSkills(
    character: CharacterEntity, skillCrossRefList: List<CharacterSkillCrossRef>
) : List<CharacterSkillCrossRef>  {

    var n : Int = 2

    when (character.rolClass) {

        RolClass.WARRIOR, RolClass.BARBARIAN -> {
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    13 -> skillCrossRef.value -= n // CURACIÓN
                    14 -> skillCrossRef.value -= n // PERCEPCIÓN
                    15 -> skillCrossRef.value -= n // MITOS Y LEYENDA

                    1 -> skillCrossRef.value += n // SUPERVIVENCIA
                    5 -> skillCrossRef.value += n // Intimidar
                    3 -> skillCrossRef.value += n // Atletismo
                    else -> {} // Otras habilidades se mantienen igual
                }
            }
        }

        RolClass.PALADIN  -> {
            // Paladín (fuerza, magia y carisma)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    6 -> skillCrossRef.value -= n // ACROBACIAS
                    9 -> skillCrossRef.value -= n // ESQUIVAR
                    10 -> skillCrossRef.value -= n // SIGILO

                    14 -> skillCrossRef.value += n // SUPERVIVENCIA
                    18 -> skillCrossRef.value += n // Intimidar
                    3 -> skillCrossRef.value += n // Atletismo
                    else -> {}
                }
            }
        }

        RolClass.BARD  -> {
            // Bardo (carisma, magia y habilidades sociales)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    17 -> skillCrossRef.value += n // INTERPRETAR
                    8 -> skillCrossRef.value += n // DEDOS ÁGILES
                    9 -> skillCrossRef.value += n // ESQUIVAR

                    3 -> skillCrossRef.value -= n // ATLETISMO
                    10 -> skillCrossRef.value -= n // SIGILO
                    1 -> skillCrossRef.value -= n // SUPERVIVENCIA
                    else -> {}
                }
            }
        }

        RolClass.ROGUE  -> {
            // Pícaro (sigilo, destreza, manipulación)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    10 -> skillCrossRef.value += n // Sigilo (clave para pícaro)
                    8 -> skillCrossRef.value += n // DEDOS ÁGILES
                    16 -> skillCrossRef.value += n // ENGAÑAR

                    13 -> skillCrossRef.value -= n // CURACIÓN
                    11 -> skillCrossRef.value -= n // ALERTA
                    1 -> skillCrossRef.value -= n // SUPERVIVENCIA
                    else -> {}
                }
            }
        }

        RolClass.EXPLORER  -> {
            // Explorador (habilidades físicas y de supervivencia)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    7 -> skillCrossRef.value += n // PESCA Y CAZA
                    1 -> skillCrossRef.value += n // SUPERVIVENCIA
                    11 -> skillCrossRef.value += n // ALERTA

                    19 -> skillCrossRef.value -= n // NEGOCIAR
                    16 -> skillCrossRef.value -= n // ENGAÑAR
                    15 -> skillCrossRef.value -= n // MITOS Y LEYENDA

                    else -> {}
                }
            }
        }

        RolClass.CLERIC  -> {
            // Clérigo (magia, curación y carisma)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    1 -> skillCrossRef.value -= n // SUPERVIVENCIA
                    6 -> skillCrossRef.value -= n // ACROBACIAS
                    10 -> skillCrossRef.value -= n // SIGILO

                    13 -> skillCrossRef.value += n // CURACIÓN
                    14 -> skillCrossRef.value += n // PERCEPCIÓN
                    15 -> skillCrossRef.value += n // MITOS Y LEYENDAS

                    else -> {}
                }
            }
        }



        RolClass.WIZARD, RolClass.WARLOCK  -> {
            // Hechicero (magia, carisma)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    10 -> skillCrossRef.value -= n // sigilo
                    6 -> skillCrossRef.value -= n // ACROBACIAS
                    3 -> skillCrossRef.value -= n // atletismo

                    11 -> skillCrossRef.value += n // ALERTA
                    14 -> skillCrossRef.value += n // PERCEPCIÓN
                    15 -> skillCrossRef.value += n // MITOS Y LEYENDAS
                    else -> {}
                }
            }
        }


        RolClass.DRUID  -> {
            // Druida (magia, naturaleza)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    2 -> skillCrossRef.value -= n // ARTESANÍA
                    6 -> skillCrossRef.value -= n // ACROBACIAS
                    19 -> skillCrossRef.value -= n // PESCA Y CAZA

                    7 -> skillCrossRef.value += n // PESCA Y CAZA
                    13 -> skillCrossRef.value += n // curación
                    12 -> skillCrossRef.value += n // naturaleza
                    else -> {}
                }
            }
        }



        else -> {
            // Si la clase no está definida, no se realizan cambios.
        }
    }
    return skillCrossRefList
}
