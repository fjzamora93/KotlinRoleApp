package com.roleapp.character.domain.usecase.character.generateutils

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.roleapp.character.data.model.local.RolClass

fun adjustCharacterSkills(
    character: CharacterEntity, skillCrossRefList: List<CharacterSkillCrossRef>
) : List<CharacterSkillCrossRef>  {

    var minus : Int = 4
    var plus : Int = 2

    when (character.rolClass) {

        RolClass.WARRIOR, RolClass.BARBARIAN -> {
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    13 -> skillCrossRef.value -= minus // CURACIÓN
                    14 -> skillCrossRef.value -= minus // PERCEPCIÓN
                    15 -> skillCrossRef.value -= minus // MITOS Y LEYENDA
                    10 -> skillCrossRef.value -= minus // SIGILO


                    1 -> skillCrossRef.value += plus // SUPERVIVENCIA
                    5 -> skillCrossRef.value += plus // Intimidar
                    3 -> skillCrossRef.value += plus // Atletismo
                    else -> {} // Otras habilidades se mantienen igual
                }
            }
        }

        RolClass.PALADIN  -> {
            // Paladín (fuerza, magia y carisma)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    6 -> skillCrossRef.value -= minus // ACROBACIAS
                    9 -> skillCrossRef.value -= minus // ESQUIVAR
                    10 -> skillCrossRef.value -= minus // SIGILO
                    12 -> skillCrossRef.value -= minus // NATURALEZA

                    14 -> skillCrossRef.value += plus // SUPERVIVENCIA
                    18 -> skillCrossRef.value += plus // Intimidar
                    3 -> skillCrossRef.value += plus // Atletismo
                    else -> {}
                }
            }
        }

        RolClass.BARD  -> {
            // Bardo (carisma, magia y habilidades sociales)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    17 -> skillCrossRef.value += plus // INTERPRETAR
                    8 -> skillCrossRef.value += plus // DEDOS ÁGILES
                    9 -> skillCrossRef.value += plus // ESQUIVAR


                    3 -> skillCrossRef.value -= minus // ATLETISMO
                    10 -> skillCrossRef.value -= minus // SIGILO
                    1 -> skillCrossRef.value -= minus // SUPERVIVENCIA
                    7 -> skillCrossRef.value -= minus // PESCA Y CAZA

                    else -> {}
                }
            }
        }

        RolClass.ROGUE  -> {
            // Pícaro (sigilo, destreza, manipulación)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    10 -> skillCrossRef.value += plus // Sigilo (clave para pícaro)
                    8 -> skillCrossRef.value += plus // DEDOS ÁGILES
                    16 -> skillCrossRef.value += plus // ENGAÑAR

                    13 -> skillCrossRef.value -= minus // CURACIÓN
                    11 -> skillCrossRef.value -= minus // ALERTA
                    1 -> skillCrossRef.value -= minus // SUPERVIVENCIA
                    15 -> skillCrossRef.value -= minus // MITOS Y LEYENDA
                    else -> {}
                }
            }
        }

        RolClass.EXPLORER  -> {
            // Explorador (habilidades físicas y de supervivencia)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    7 -> skillCrossRef.value += plus // PESCA Y CAZA
                    1 -> skillCrossRef.value += plus // SUPERVIVENCIA
                    11 -> skillCrossRef.value += plus // ALERTA

                    19 -> skillCrossRef.value -= minus // NEGOCIAR
                    16 -> skillCrossRef.value -= minus // ENGAÑAR
                    15 -> skillCrossRef.value -= minus // MITOS Y LEYENDA
                    13 -> skillCrossRef.value -= minus // CURACIÓN

                    else -> {}
                }
            }
        }

        RolClass.CLERIC  -> {
            // Clérigo (magia, curación y carisma)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    1 -> skillCrossRef.value -= minus // SUPERVIVENCIA
                    6 -> skillCrossRef.value -= minus // ACROBACIAS
                    10 -> skillCrossRef.value -= minus // SIGILO
                    19 -> skillCrossRef.value -= minus // NEGOCIAR

                    13 -> skillCrossRef.value += plus // CURACIÓN
                    14 -> skillCrossRef.value += plus // PERCEPCIÓN
                    15 -> skillCrossRef.value += plus // MITOS Y LEYENDAS

                    else -> {}
                }
            }
        }



        RolClass.WIZARD, RolClass.WARLOCK  -> {
            // Hechicero (magia, carisma)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    10 -> skillCrossRef.value -= minus // sigilo
                    6 -> skillCrossRef.value -= minus // ACROBACIAS
                    3 -> skillCrossRef.value -= minus // atletismo
                    19 -> skillCrossRef.value -= minus // NEGOCIAR

                    11 -> skillCrossRef.value += plus // ALERTA
                    14 -> skillCrossRef.value += plus // PERCEPCIÓN
                    15 -> skillCrossRef.value += plus // MITOS Y LEYENDAS
                    else -> {}
                }
            }
        }


        RolClass.DRUID  -> {
            // Druida (magia, naturaleza)
            skillCrossRefList.forEach { skillCrossRef ->
                when (skillCrossRef.skillId) {
                    2 -> skillCrossRef.value -= minus // ARTESANÍA
                    6 -> skillCrossRef.value -= minus // ACROBACIAS
                    19 -> skillCrossRef.value -= minus // PESCA Y CAZA
                    16 -> skillCrossRef.value -= minus // ENGAÑAR

                    7 -> skillCrossRef.value += plus // PESCA Y CAZA
                    13 -> skillCrossRef.value += plus // curación
                    12 -> skillCrossRef.value += plus // naturaleza
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
