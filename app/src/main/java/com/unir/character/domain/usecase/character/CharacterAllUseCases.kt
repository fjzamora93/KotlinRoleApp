package com.unir.character.domain.usecase.character

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.domain.repository.CharacterRepository
import javax.inject.Inject



// Obtener personajes por User ID
class GetCharactersByUserIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(userId: Int): Result<List<CharacterEntity>> {
        val result = repository.getCharactersByUserId(userId)
        return if (result.isSuccess) {
            Result.success(result.getOrNull() ?: emptyList())
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}


// Obtener personaje por ID
class GetCharacterByIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Long): Result<CharacterEntity?> {
        val result = repository.getCharacterById(id)
        return if (result.isSuccess) {
            Result.success(result.getOrNull())
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Character not found"))
        }
    }
}





// Eliminar personaje
class DeleteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterEntity): Result<Unit> {
        val result = repository.deleteCharacter(character)
        return if (result.isSuccess) {
            Result.success(Unit) // Indicar que la eliminación fue exitosa
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Error al eliminar el personaje"))
        }
    }
}







