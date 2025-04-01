package com.roleapp.character.domain.usecase.character

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.domain.repository.CharacterRepository
import javax.inject.Inject

class UpdateCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterEntity): Result<CharacterEntity> {

        val updatedCharacter = character.copy(updatedAt = System.currentTimeMillis())
        return try {
            repository.saveCharacter(updatedCharacter)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}