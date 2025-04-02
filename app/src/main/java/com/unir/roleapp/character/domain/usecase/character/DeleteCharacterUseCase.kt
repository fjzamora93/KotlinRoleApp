package com.roleapp.character.domain.usecase.character

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.domain.repository.CharacterRepository
import javax.inject.Inject


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







