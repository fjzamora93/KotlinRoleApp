package com.roleapp.character.domain.usecase.character.generateutils

import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.roleapp.character.ui.screens.characterform.components.PersonalityTestForm


fun calculateTestResult(
    form: PersonalityTestForm,
    originalCrossRef: List<CharacterSkillCrossRef>
): MutableList<CharacterSkillCrossRef> {
    val n = 4

    // Creamos una copia mutable de la lista original para hacer modificaciones
    val skillCrossRefList = originalCrossRef.toMutableList()
    val skillMap = skillCrossRefList.associateBy { it.skillId }.toMutableMap()

    fun modifySkill(skillId: Int, newValue: Int? = null, delta: Int = 0, divideByTwo: Boolean = false) {
        skillMap[skillId]?.apply {
            when {
                newValue != null -> value = newValue // Asignación directa si se especifica
                divideByTwo -> value /= 2
                else -> value += delta
            }
        }
    }

    // Mapa de estilos de combate (ID de habilidad por estilo)
    val combatSkills = mapOf(
        "Combate a distancia" to 21,
        "Dagas, cuchillos y espadas cortas" to 22,
        "Lanzas, varas largas o guadañas" to 23,
        "Armas pesadas como mazas o martillos" to 24,
        "Espadas" to 25
    )

    // 1. Primero establecemos TODAS las habilidades de combate a 7
    combatSkills.values.forEach { skillId ->
        modifySkill(skillId, newValue = 5)
    }

    // 2. Luego establecemos la habilidad seleccionada a 14
    combatSkills[form.combatStyle]?.let { selectedSkillId ->
        modifySkill(selectedSkillId, newValue = 14)
    }

    // Resto de la lógica original (sociability, hobbies, etc.)
    // Sociability
    val sociabilitySkills = mapOf(
        "No confío ni siquiera en mi sombra" to 16,
        "Me encanta dramatizar, ¿a quién no?" to 17,
        "Diplomacia ante todo" to 18,
        "Lo mío es la seducción" to 20
    )
    sociabilitySkills[form.sociability]?.let { modifySkill(it, delta = n) }

    if (form.sociability == "¿Hablar? Mejor partirles la boca") {
        modifySkill(5, delta = n)
        (16..18).forEach { modifySkill(it, divideByTwo = true) }
    }

    // Hobbies
    val hobbiesSkills = mapOf(
        "Estoy en contacto con la naturaleza" to listOf(1, 12),
        "Me gusta crear mis propias herramientas" to listOf(2),
        "Procuro mantener mi cuerpo activo" to listOf(3),
        "Soy una rata de biblioteca" to listOf(15),
        "Intento estar siempre rodeado de gente" to listOf(18, 20)
    )
    hobbiesSkills[form.hobbies]?.forEach { modifySkill(it, delta = n) }

    // Afraid
    val afraidModifications = mapOf(
        "No me gustan las criaturas más grandes que yo" to listOf(1 to -n, 12 to -n),
        "El agua... de hecho no sé nadar" to listOf(4 to 0, 5 to -n),
        "Odio al mundo y socializar con otras personas" to (16..20).map { it to 0 },
        "No me gusta el combate, estoy siempre lo más alejado posible" to
                (listOf(3 to -n, 5 to -n, 9 to n) + (22..25).map { it to 0 }),
        "Soy algo torpe. Muy torpe." to (6..8).map { it to -n }
    )
    afraidModifications[form.afraid]?.forEach { (skillId, delta) ->
        modifySkill(skillId, delta = delta, divideByTwo = delta == 0)
    }

    // Proficiency
    val proficiencySkills = mapOf(
        "Puedo pasar inadvertido en cualquier lugar" to 10,
        "Tengo un don natural para realizar sanaciones" to 13,
        "Siempre alerta, nunca desprevenido" to 11,
        "Estoy atento a los detalles, no se me escapa nada" to 14,
        "Me basto y me sobro con mi belleza" to 20
    )
    proficiencySkills[form.proeficiency]?.let { modifySkill(it, delta = n) }

    // REDUCE INTIMIDACIÓN
    if (form.proeficiency == "Tengo un don natural para realizar sanaciones") {
        modifySkill(5, delta = -n)
    }
    if (form.proeficiency == "Me basto y me sobro con mi belleza") {
        modifySkill(5, divideByTwo = true)
    }

    return skillCrossRefList
}