package com.unir.character.domain.usecase.character


data class CharacterUseCases(
    val getCharactersByUserId: GetCharactersByUserUseCase,
    val getCharacterById: GetCharacterByIdUseCase,
    val saveCharacter: SaveCharacterUseCase,
    val deleteCharacter: DeleteCharacterUseCase,
    val updateCharacter: UpdateCharacterUseCase,
    val createCharacter: CreateCharacterUseCase
)
