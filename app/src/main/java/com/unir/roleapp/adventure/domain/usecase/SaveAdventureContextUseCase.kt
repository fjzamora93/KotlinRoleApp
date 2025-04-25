package com.unir.roleapp.adventure.domain.usecase

import com.unir.roleapp.adventure.domain.model.CharacterContext

interface SaveAdventureContextUseCase {
    /** Guarda el contexto histórico y los contextos de personajes */
    suspend operator fun invoke(
        adventureId: String,
        historicalContext: String,
        characterContexts: List<CharacterContext>
    ): Result<Unit>
}