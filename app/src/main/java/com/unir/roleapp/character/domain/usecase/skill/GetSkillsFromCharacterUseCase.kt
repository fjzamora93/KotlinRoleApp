package com.roleapp.character.domain.usecase.skill

import com.roleapp.character.data.model.local.SkillValue
import com.roleapp.character.domain.repository.SkillRepository
import javax.inject.Inject


class GetSkillsFromCharacterUseCase @Inject constructor(private val repository: SkillRepository) {
    suspend operator fun invoke(
        characterId: Long)
    : Result<List<SkillValue>> {

        val localResult = repository.getSkillsFromCharacter(characterId)
        val list = localResult.getOrNull()
        localResult.onSuccess {
            println("Obteniendo habilidades desde la base de datos local  $it ")
        }

        if (list != null) {
            if (list.isNotEmpty())
                if (localResult.isSuccess && localResult.getOrNull() != null) {
                    println("Obteniendo habilidades desde la base de datos local  ${localResult.getOrNull()} ")
                    return localResult
                }
        }

        println("Obteniendo habilidades desde la base de datos REMOTAAA  ${repository.fetchCharacterSkillsFromApi(characterId).getOrNull()} ")
        return repository.fetchCharacterSkillsFromApi(characterId)

    }
}





