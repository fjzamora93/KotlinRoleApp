package com.unir.roleapp.character.domain.usecase.item

import com.unir.roleapp.character.data.model.local.Item
import com.unir.roleapp.character.domain.repository.ItemRepository
import com.unir.roleapp.character.domain.usecase.character.CharacterUseCases
import javax.inject.Inject

class GetItemsBySessionUseCase @Inject constructor(
    private val repository: ItemRepository,
    private val characterUseCase: CharacterUseCases
) {
    suspend operator fun invoke(): Result<List<Item>> {
        // Obtenemos el personaje activo
        try {
            val character = characterUseCase.getActiveCharacter().getOrNull()

            // Verificamos si el personaje activo es válido
            if (character?.gameSessionId != null) {
                return repository.getItemsBySession(character.gameSessionId!!)
            }
            return Result.failure(Exception("No se encontró el personaje activo"))

        } catch (e: Exception) {
            return Result.failure(e)
        }

    }
}
