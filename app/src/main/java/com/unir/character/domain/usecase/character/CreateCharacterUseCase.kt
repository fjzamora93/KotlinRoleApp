package com.unir.character.domain.usecase.character

import com.unir.auth.data.model.User
import com.unir.auth.domain.repository.UserRepository
import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.Skill
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.repository.SkillRepository
import com.unir.character.domain.usecase.character.generateutils.adjustCharacterSkills
import com.unir.character.domain.usecase.character.generateutils.calculateSkills
import com.unir.character.domain.usecase.character.generateutils.calculateTestResult
import com.unir.character.domain.usecase.character.generateutils.configureCharacterSkills
import com.unir.character.domain.usecase.character.generateutils.generateStats
import com.unir.character.ui.screens.skills.PersonalityTestForm
import javax.inject.Inject

class CreateCharacterUseCase @Inject constructor(
    private val repository: SkillRepository,
    private val userRepo: UserRepository
) {
    suspend operator fun invoke(
        plainCharacter: CharacterEntity,
        form: PersonalityTestForm
    ): Result<CharacterEntity> {
        return try {
            val userResult: Result<User> = userRepo.getUser()
            val user = userResult.getOrNull()

            plainCharacter.userId = user?.id ?: throw NoSuchElementException("Usuario no encontrado")
            plainCharacter.updatedAt = System.currentTimeMillis()
            plainCharacter.id = "${plainCharacter.userId}${plainCharacter.updatedAt}".toLong()

            val character: CharacterEntity = generateStats(plainCharacter)


            var skillList: List<Skill> = emptyList()
            repository.getSkills().onSuccess{  skills -> skillList = skills }.onFailure { throw NoSuchElementException("NO se han recuperado las habilidades")  }

            val characterSkillCrossRef = configureCharacterSkills(character, form, skillList)

            return repository.saveCharacterWithSKills(character, characterSkillCrossRef)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}