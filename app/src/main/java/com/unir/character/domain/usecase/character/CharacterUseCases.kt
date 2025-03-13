package com.unir.character.domain.usecase.character


data class CharacterUseCases(
    val getCharactersByUserId: GetCharactersByUserIdUseCase,
    val getCharacterById: GetCharacterByIdUseCase,
    val updateCharacter: UpdateCharacterUseCase,
    val deleteCharacter: DeleteCharacterUseCase,
)
