package com.unir.roleapp.character.domain.usecase.character

import com.unir.roleapp.character.data.model.local.CharacterEntity
import com.unir.roleapp.character.domain.repository.CharacterRepository
import com.unir.roleapp.character.domain.usecase.character.generateutils.calculateCharacterActionPoints
import com.unir.roleapp.character.domain.usecase.character.generateutils.calculateCharacterHealth
import javax.inject.Inject

class UpdateCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterEntity): Result<CharacterEntity> {

        val hp = calculateCharacterHealth(character)
        val ap = calculateCharacterActionPoints(character)

        val updatedCharacter = character.copy(
            ap = ap,
            hp = hp,
            updatedAt = System.currentTimeMillis()
        )


        return try {
            repository.saveCharacter(updatedCharacter)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}