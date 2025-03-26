package com.unir.character.domain.usecase.character.generateutils

import com.unir.character.data.model.local.CharacterSkillCrossRef
import com.unir.character.ui.screens.characterform.components.PersonalityTestForm


fun calculateTestResult(
    form: PersonalityTestForm,
    skillCrossRefList: List<CharacterSkillCrossRef> // Lista donde se almacenan las referencias cruzadas
): List<CharacterSkillCrossRef> {
    var n: Int = 3

    // Combat Style (responde a habilidades relacionadas con combate, STR, DEX)
    when (form.combatStyle) {
        "Combate a distancia" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 21 }
            skillCrossRef?.let {
                it.value += n
            }
        }
        "Dagas, cuchillos y espadas cortas" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 22 }
            skillCrossRef?.let {
                it.value += n
            }
        }
        "Lanzas, varas largas o guadañas" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 23 }
            skillCrossRef?.let {
                it.value += n
            }
        }
        "Armas pesadas como mazas o martillos" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 24 }
            skillCrossRef?.let {
                it.value += n
            }
        }
        "Espadas" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 25 }
            skillCrossRef?.let {
                it.value += n
            }
        }
    }

    // Sociability (relacionado con Carisma (CAR))
    when (form.sociability) {
        "No confío ni siquiera en mi sombra" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 16 }
            skillCrossRef?.let {
                it.value += n
            }
        }
        "Me encanta dramatizar, ¿a quién no?" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 17 }
            skillCrossRef?.let {
                it.value += n
            }
        }
        "Diplomacia ante todo" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 18 }
            skillCrossRef?.let {
                it.value += n
            }
        }

        "Lo mío es la seducción" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 20 }
            skillCrossRef?.let {
                it.value += n
            }
        }
        // INTIMIDAR - Bajamos todas las que no son INTIMIDAR
        "¿Hablar? Mejor partirles la boca" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 5 }
            skillCrossRef?.let {
                it.value += n
            }

            (16..18).forEach { skillId ->
                val skillCrossRef = skillCrossRefList.find { it.skillId == skillId }
                skillCrossRef?.let {
                    it.value /= 2
                }
            }
        }
    }

    // Hobbies (relacionado con habilidades de DEX y posiblemente INT)
    when (form.hobbies) {
        "Estoy en contacto con la naturaleza" -> {
            val skillCrossRef1 = skillCrossRefList.find { it.skillId == 1 }
            skillCrossRef1?.let {
                it.value += n
            }
            val skillCrossRef12 = skillCrossRefList.find { it.skillId == 12 }
            skillCrossRef12?.let {
                it.value += n
            }
        }
        "Me gusta crear mis propias herramientas" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 2}
            skillCrossRef?.let {
                it.value += n
            }
        }
        "Procuro mantener mi cuerpo activo" -> {
            val skillCrossRef3 = skillCrossRefList.find { it.skillId == 3 }
            skillCrossRef3?.let {
                it.value += n
            }
        }

        "Soy una rata de biblioteca" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 15 }
            skillCrossRef?.let {
                it.value += n
            }

        }
        "Intento estar siempre rodeado de gente" -> {
            val skillCrossRef = skillCrossRefList.find { it.skillId == 20 }
            skillCrossRef?.let {
                it.value += n
            }
            val skillCrossRef18 = skillCrossRefList.find { it.skillId == 18 }
            skillCrossRef18?.let {
                it.value += n
            }
        }
    }

    // Afraid (relacionado con habilidades de STR, DEX, CAR, etc.)
    when (form.afraid) {
        "No me gustan las criaturas más grandes que yo" -> {
            // Reducción de habilidades relacionadas con supervivencia y fuerza
            val survivalSkill = skillCrossRefList.find { it.skillId == 1 }
            survivalSkill?.let {
                it.value -= n
            }

            val natureSkill = skillCrossRefList.find { it.skillId == 12 }
            natureSkill?.let {
                it.value -= n
            }
        }

        "El agua... de hecho no sé nadar" -> {
            // Reducción de habilidades relacionadas con agua y combate
            val swimmingSkill = skillCrossRefList.find { it.skillId == 4 }
            swimmingSkill?.let {
                it.value /= 2
            }

            val intimidateSkill = skillCrossRefList.find { it.skillId == 5 } // Intimidar
            intimidateSkill?.let {
                it.value -= n
            }
        }

        "Odio al mundo y socializar con otras personas" -> {
            (16..20).forEach { skillId ->
                val skillCrossRef = skillCrossRefList.find { it.skillId == skillId }
                skillCrossRef?.let {
                    it.value /= 2
                }
            }
        }

        "No me gusta el combate, estoy siempre lo más alejado posible" -> {
            // Reducción de habilidades de combate
            val athleticsSkill = skillCrossRefList.find { it.skillId == 3 } // Atletismo
            athleticsSkill?.let {
                it.value -= n
            }

            val intimidateSkill = skillCrossRefList.find { it.skillId == 5 } // Intimidar
            intimidateSkill?.let {
                it.value -= n
            }

            for (skillId in 22..25) {
                val skill = skillCrossRefList.find { it.skillId == skillId }
                skill?.let {
                    it.value /= 2
                }
            }

            val esquivarSkill = skillCrossRefList.find { it.skillId == 9 } // Atletismo
            esquivarSkill?.let {
                it.value += n
            }

        }

        "Soy algo torpe. Muy torpe." -> {
            for (skillId in 6..8) {
                val skill = skillCrossRefList.find { it.skillId == skillId }
                skill?.let {
                    it.value -= n
                }
            }
        }
    }


    // Proficiency (relacionado con habilidades pasivas, tal vez DEX, INT o CAR)
    when (form.proeficiency) {
        // "Puedo pasar inadvertido en cualquier lugar"
        "Puedo pasar inadvertido en cualquier lugar" -> {
            // Modificar habilidades relacionadas con el sigilo o la evasión
            val stealthSkill = skillCrossRefList.find { it.skillId == 10 } // Sigilo (CAR)
            stealthSkill?.let {
                it.value += n  // Aumentar habilidad de sigilo
            }

        }

        // "Tengo un don natural para realizar sanaciones"
        "Tengo un don natural para realizar sanaciones" -> {
            // Aumentar habilidad de curación
            val healingSkill = skillCrossRefList.find { it.skillId == 13 } // Curación (INT)
            healingSkill?.let {
                it.value += n  // Aumentar habilidad de curación
            }

            val combatSkill = skillCrossRefList.find { it.skillId == 5 } // Intimidar (FUE)
            combatSkill?.let {
                it.value -= n
            }
        }

        // "Siempre alerta, nunca desprevenido"
        "Siempre alerta, nunca desprevenido" -> {
            // Aumentar habilidad de alerta
            val alertSkill = skillCrossRefList.find { it.skillId == 11 } // Alerta (INT)
            alertSkill?.let {
                it.value += n  // Aumentar habilidad de alerta
            }

        }

        // "Estoy atento a los detalles, no se me escapa nada"
        "Estoy atento a los detalles, no se me escapa nada" -> {
            // Aumentar habilidad de percepción
            val perceptionSkill = skillCrossRefList.find { it.skillId == 14 } // Percepción (INT)
            perceptionSkill?.let {
                it.value += n  // Aumentar habilidad de percepción
            }

        }

        // "Me basto y me sobro con mi belleza"
        "Me basto y me sobro con mi belleza" -> {
            // Aumentar habilidades relacionadas con el carisma
            val seductionSkill = skillCrossRefList.find { it.skillId == 20 } // Seducir (CAR)
            seductionSkill?.let {
                it.value += n
            }

            // Reducir habilidades relacionadas con la agresividad
            val intimidateSkill = skillCrossRefList.find { it.skillId == 5 }
            intimidateSkill?.let {
                it.value /= 2
            }
        }
    }


    // Devolvemos la lista con las referencias cruzadas modificadas
    return skillCrossRefList
}
