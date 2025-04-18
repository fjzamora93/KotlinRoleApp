package com.roleapp.character.domain.usecase.character

import com.roleapp.auth.domain.usecase.user.UserUseCase
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.Skill
import com.roleapp.character.domain.repository.CharacterRepository
import com.roleapp.character.domain.usecase.character.generateutils.configureCharacterSkills
import com.roleapp.character.domain.usecase.character.generateutils.generateStats
import com.roleapp.character.domain.usecase.skill.SkillUseCases
import com.roleapp.character.ui.screens.characterform.components.PersonalityTestForm
import com.unir.roleapp.character.domain.usecase.character.generateutils.calculateCharacterActionPoints
import com.unir.roleapp.character.domain.usecase.character.generateutils.calculateCharacterHealth
import javax.inject.Inject

class CreateCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val skillUseCases: SkillUseCases,
    private val userUseCase: UserUseCase
) {
    suspend operator fun invoke(
        plainCharacter: CharacterEntity,
        form: PersonalityTestForm
    ): Result<CharacterEntity> {
        return try {
            val userResult = userUseCase.getUser()
            val userId = userResult.getOrNull()?.id ?: return Result.failure(Exception("User not found"))

            plainCharacter.userId = userId
            plainCharacter.updatedAt = System.currentTimeMillis()



            plainCharacter.id = "${plainCharacter.userId}${plainCharacter.updatedAt}".toLong()

            val character: CharacterEntity = generateStats(plainCharacter)



            var skillList: List<Skill> = emptyList()
            skillUseCases.getSkills().onSuccess{ skills -> skillList = skills }.onFailure { throw NoSuchElementException("NO se han recuperado las habilidades")  }

            val characterSkillCrossRef = configureCharacterSkills(character, form, skillList)
            val hp = calculateCharacterHealth(character)
            val ap = calculateCharacterActionPoints(character)

            val updatedCharacter = character.copy(
                ap = ap,
                hp = hp,
            )

            return characterRepository.saveCharacterWithSKills(updatedCharacter, characterSkillCrossRef)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}