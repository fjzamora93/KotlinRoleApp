package com.unir.character.domain.usecase.character

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.usecase.character.generateutils.generateStats
import java.time.LocalDateTime
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