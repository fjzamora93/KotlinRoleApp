package com.unir.roleapp.adventure.data.repository

import com.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.adventure.domain.model.CharacterContext
import kotlinx.coroutines.flow.Flow

/**
 * Contrato para acceder y guardar el contexto de la aventura.
 */
interface AdventureContextRepository {
    /**
     * Emite en tiempo real el contexto histórico de la aventura.
     */
    fun getHistoricalContextStream(adventureId: String): Flow<String>

    /**
     * Emite en tiempo real la lista de personajes de la aventura.
     */
    fun getCharactersStream(adventureId: String): Flow<List<CharacterEntity>>

    /**
     * Actualiza solo el contexto histórico en Firestore.
     */
    suspend fun updateHistoricalContext(
        adventureId: String,
        historicalContext: String
    ): Result<Unit>

    /**
     * Actualiza el contexto de un personaje individual en Firestore.
     * Ahora acepta directamente el characterId y el texto.
     */
    suspend fun updateCharacterContext(
        adventureId: String,
        characterId: Long,
        context: String
    ): Result<Unit>

    /**
     * Guarda el contexto histórico y los contextos individuales de personajes.
     */
    suspend fun saveContext(
        adventureId: String,
        historicalContext: String,
        charContexts: List<CharacterContext>
    ): Result<Unit>
}

