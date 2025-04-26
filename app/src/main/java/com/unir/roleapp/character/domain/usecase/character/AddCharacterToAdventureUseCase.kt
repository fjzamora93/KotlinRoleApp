package com.unir.roleapp.character.domain.usecase.character

import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.domain.repository.CharacterRepository
import com.unir.roleapp.character.data.model.remote.FirebaseCharacter
import javax.inject.Inject


class AddCharacterToAdventureUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,

    ) {
    suspend operator fun invoke(character: CharacterEntity, sessionId : String): Result<CharacterEntity> {

        val characterSummary = FirebaseCharacter(
            id = character.id,
            userId = character.userId,
            updatedAt = character.updatedAt,

            name = character.name,
            description = character.description,
            race = character.race.name,
            armor = character.armor,

            level = character._level,
            hp = character.hp,
            currentHp = character.currentHp,
            ap = character.ap,
            currentAp = character.currentAp,

            imgUrl = character.imgUrl,
            gameSessionId = sessionId
        )

            return characterRepository.addCharacterToAdventure(characterSummary, sessionId)
    }
}