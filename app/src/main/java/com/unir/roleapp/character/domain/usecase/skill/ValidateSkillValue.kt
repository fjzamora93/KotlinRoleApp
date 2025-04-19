package com.roleapp.character.domain.usecase.skill

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.SkillValue
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
    operator fun invoke(
        character: CharacterEntity,
        skillValues: List<SkillValue>
    ): Result<SkillValidationResult> {
        val puntosDisponibles = calculateAvailablePoints(character, skillValues)

        return if (puntosDisponibles < 0) {
            Result.success(
                SkillValidationResult.Error(
                    message = "Has asignado más puntos de los disponibles.",
                    puntosDisponibles = puntosDisponibles,
                    correctedSkills = skillValues
                )
            )
        } else {
            Result.success(
                SkillValidationResult.Success(
                    puntosDisponibles = puntosDisponibles
                )
            )
        }
    }

    private fun calculateAvailablePoints(character: CharacterEntity, skillValues: List<SkillValue>): Int {
        val regularPoints = 175
        val weaponPoints = 34
        val totalPoints = 3 * character.level + regularPoints + weaponPoints
        return totalPoints - skillValues.sumOf { it.value }
    }
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


