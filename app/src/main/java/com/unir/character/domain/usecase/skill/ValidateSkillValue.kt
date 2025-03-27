package com.unir.character.domain.usecase.skill

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.SkillValue
import javax.inject.Inject



/**
 * VALIDACIÓN DE PUNTOS A REPARTIR
 *
 *
 * PUntos totales a repartir = PuntosXNivel + PuntosBase
 * Ejemplo:      3 * character.level + 50
 *
 *PUntos repartidos:
 * skillList.sumOf { it.value }
 *
 * PUntos disponibles = Puntos totales a repartir - Puntos repartidos
 * Ejemplo: puntos_disponibles = 3 * character.level + 50 - skillList.sumOf { it.value }
 *
 *
 * */
class ValidateSkillValue @Inject constructor() {
    suspend operator fun invoke(
        character: CharacterEntity,
        skillValues: List<SkillValue>
    ): Result<SkillValidationResult> {
        val correctedSkills = skillValues.toMutableList()
        val corrections = mutableListOf<String>()




        skillValues.forEachIndexed { index, skillValue ->
            val originalValue = skillValue.value
            when {
                originalValue < 5 -> {
                    correctedSkills[index] = skillValue.copy(value = 5)
                    corrections.add("${skillValue.skill.name} ($originalValue → 5)")
                }
                originalValue > 15 -> {
                    correctedSkills[index] = skillValue.copy(value = 15)
                    corrections.add("${skillValue.skill.name} ($originalValue → 15)")
                }
            }
        }

         if (corrections.isNotEmpty()) {
             return Result.success(SkillValidationResult.Error(
                message = "Correcciones: ${corrections.joinToString()}",
                puntosDisponibles = calculateAvailablePoints(character, correctedSkills),
                correctedSkills = correctedSkills
            ))
        }




        return Result.success(SkillValidationResult.Success(
            puntosDisponibles = calculateAvailablePoints(character, skillValues)
        ))
    }
}

/**
 * Calcula los puntos totales a repartir basados en el nivel del personaje. NO ESTÁN INCLUIDOS LOS PUNTOS DE ARMAS.
 *
 * @param level El nivel del personaje.
 * @return Los puntos totales a repartir.
 */


private fun calculateAvailablePoints(character: CharacterEntity, skillValues: List<SkillValue>): Int {
    val regularPoints = 175
    val weaponPoints = 34 // 5 de cada grupo de armas y 14 para el arma principal
    val totalPoints = 3 * character.level + regularPoints + weaponPoints
    return totalPoints - skillValues.sumOf { it.value }
}

/**
 * Resultado de la validación.
 */
sealed class SkillValidationResult {
    data class Success(val puntosDisponibles: Int) : SkillValidationResult()
    data class Error(
        val message: String,
        val puntosDisponibles: Int,
        val correctedSkills: List<SkillValue> // Agregamos esta propiedad para pasar las habilidades corregidas
    ) : SkillValidationResult()
}


