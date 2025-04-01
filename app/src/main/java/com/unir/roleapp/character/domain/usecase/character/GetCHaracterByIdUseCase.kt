package com.roleapp.character.domain.usecase.character

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.domain.repository.CharacterRepository
import javax.inject.Inject


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