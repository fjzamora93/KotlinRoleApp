package com.roleapp.character.domain.usecase.skill

import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.domain.repository.SkillRepository
import javax.inject.Inject


class GetSkillsFromCharacterUseCase @Inject constructor(private val repository: SkillRepository) {
    suspend operator fun invoke(characterId: Long): Result<List<SkillValue>> = repository.getSkillsFromCharacter(characterId)
}





