package com.unir.character.domain.usecase.item

import com.unir.character.data.model.local.CharacterItemDetail
import com.unir.character.domain.repository.ItemRepository
import com.unir.character.domain.usecase.character.CharacterUseCases
import javax.inject.Inject

class GetItemsByCharacter @Inject constructor(
    private val repository: ItemRepository,
    private val characterUseCase: CharacterUseCases
) {
    suspend operator fun invoke(): Result<List<CharacterItemDetail>> {
        return try {
            // Obtenemos el personaje activo
            val character = characterUseCase.getActiveCharacter().getOrNull()

            // Verificamos si el personaje activo es válido
            if (character != null && character.id != null) {
                val items = repository.getItemsByCharacterId(character.id)
                return if (items.getOrNull().isNullOrEmpty()) {
                    Result.failure(Exception("No se encontraron ítems para el personaje con ID ${character.id}"))
                } else {
                    Result.success(items.getOrNull() ?: emptyList()) // Retornamos los ítems si no están vacíos
                }
            } else {
                // Si no hay un personaje activo o el ID es nulo, retornamos un error
                Result.failure(Exception("No se encontró el personaje activo"))
            }
        } catch (e: Exception) {
            // En caso de error, devolvemos la excepción
            Result.failure(e)
        }
    }
}

