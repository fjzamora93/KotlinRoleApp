package com.unir.sheet.domain.usecase.skill

import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.repository.SkillRepositoryImpl
import javax.inject.Inject

class GetAllSkillsUseCase @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(): Result<List<Skill>> = repository.getAllSkills()
}

class GetSkillsFromCharacterUseCase @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(characterId: Int): Result<List<Skill>> = repository.getSkillsFromCharacter(characterId)
}

class DeleteSkillFromCharacterUseCase @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(characterId: Int, skillId: Int): Result<Unit> = repository.deleteSkillFromCharacter(characterId, skillId)
}

class AddSkillToCharacterUseCase @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(
        character: CharacterEntity,
        skill: Skill
    ): Result<Unit> {
        return repository.addSkillToCharacter(character.id!!, skill.id!!)
    }
}
