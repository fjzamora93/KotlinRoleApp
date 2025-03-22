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

    /**
     * Realiza la validación de los puntos de habilidades.
     *
     * @param character El personaje cuyas habilidades se están validando.
     * @param skillValues La lista de habilidades con sus valores.
     * @return Un [Result] que contiene un [SkillValidationResult] con los puntos disponibles y un mensaje de error si la validación falla.
     */
    suspend operator fun invoke(
        character: CharacterEntity,
        skillValues: List<SkillValue>
    ): Result<SkillValidationResult> {
        return try {
            // Calcular los puntos totales a repartir
            val puntosTotales = calculateTotalPoints(character.level)

            // Calcular los puntos repartidos
            val puntosRepartidos = skillValues.sumOf { it.value }

            // Calcular los puntos disponibles
            val puntosDisponibles = puntosTotales - puntosRepartidos

            // Validar si los puntos repartidos son válidos
            if (puntosRepartidos <= puntosTotales) {
                Result.success(SkillValidationResult.Success(puntosDisponibles))
            } else {
                Result.success(
                    SkillValidationResult.Error(
                    message = "Has excedido el límite de puntos. Te faltan ${-puntosDisponibles} puntos por asignar.",
                    puntosDisponibles = puntosDisponibles
                ))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Calcula los puntos totales a repartir basados en el nivel del personaje.
     *
     * @param level El nivel del personaje.
     * @return Los puntos totales a repartir.
     */
    private fun calculateTotalPoints(level: Int): Int {
        return 3 * level + 100 // Fórmula para calcular los puntos totales
    }

    /**
     * Resultado de la validación.
     */
    sealed class SkillValidationResult {
        data class Success(val puntosDisponibles: Int) : SkillValidationResult()
        data class Error(val message: String, val puntosDisponibles: Int) : SkillValidationResult()
    }

}