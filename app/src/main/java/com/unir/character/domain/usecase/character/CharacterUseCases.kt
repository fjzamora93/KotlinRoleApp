package com.unir.character.domain.usecase.character


data class CharacterUseCases(
    val getCharactersByUserId: GetCharactersByUserIdUseCase,
    val getCharacterById: GetCharacterByIdUseCase,
    val updateCharacter: CreateNewCharacterUseCase,
    val deleteCharacter: DeleteCharacterUseCase,
)
