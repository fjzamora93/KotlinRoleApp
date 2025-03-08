package com.unir.sheet.domain.usecase.skill

import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.RolClass
import com.unir.sheet.data.model.Skill
import com.unir.sheet.data.repository.SkillRepositoryImpl
import javax.inject.Inject

class GetAllSkillsUseCase @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(): Result<List<Skill>> = repository.getAllSkills()
}

class GetSkillsFromCharacterUseCase @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(characterId: Long): Result<List<Skill>> = repository.getSkillsFromCharacter(characterId)
}

class DeleteSkillFromCharacterUseCase @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(characterId: Long, skillId: Int): Result<Unit> = repository.deleteSkillFromCharacter(characterId, skillId)
}

/**
 * Caso de uso llamado desde la inserción del personaje.
 *
 *
 * */
class AddDefaultSkills @Inject constructor(private val repository: SkillRepositoryImpl) {
    suspend operator fun invoke(
        character: CharacterEntity,
    ): Result<Unit> {

        val skillIndex = when (character.rolClass) {
            RolClass.WARRIOR -> listOf(1, 2, 3)
            RolClass.BARBARIAN -> listOf(1, 4, 12)
            RolClass.MONK -> listOf(2, 4, 5)
            RolClass.PALADIN -> listOf(3, 15, 16)
            RolClass.CLERIC -> listOf(4, 14, 16)

            RolClass.ROGUE -> listOf(5, 6, 7)
            RolClass.EXPLORER -> listOf(5, 8, 11)
            RolClass.BARD -> listOf(7, 10, 15)

            RolClass.WIZARD -> listOf(9, 11, 13)
            RolClass.SORCERER -> listOf(9, 8, 17)
            RolClass.WARLOCK -> listOf(9, 15, 16)
            RolClass.DRUID -> listOf(9, 12, 14)


            RolClass.NULL -> listOf(2, 13, 17)
        }

        return try {
            repository.addDefaultSkills(character.id!!, skillIndex)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
