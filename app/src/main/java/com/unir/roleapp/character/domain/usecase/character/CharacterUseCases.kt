package com.roleapp.character.domain.usecase.character

import com.unir.roleapp.character.domain.usecase.character.AddCharacterToAdventureUseCase


data class CharacterUseCases(
    val getCharactersByUserId: GetCharactersByUserUseCase,
    val getCharacterById: GetCharacterByIdUseCase,
    val saveCharacter: SaveCharacterUseCase,
    val deleteCharacter: DeleteCharacterUseCase,
    val updateCharacter: UpdateCharacterUseCase,
    val createCharacter: CreateCharacterUseCase,
    val getActiveCharacter: GetActiveCharacterIdUseCase,
    val addCharacterToAdventure: AddCharacterToAdventureUseCase
)
