package com.unir.character.domain.usecase.skill

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.SkillValue
import javax.inject.Inject

class ValidateSkillValue @Inject constructor() {
    suspend operator fun invoke(
        character: CharacterEntity,
        skillValue: SkillValue
    ): Result<Boolean> {
        return try {
            val isValid = skillValue.value >= 0
            Result.success(isValid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}