package com.unir.sheet.domain.usecase.character


data class CharacterUseCases(
    val getAllCharacters: GetAllCharactersUseCase,
    val getCharacterByUserId: GetCharacterByUserIdUseCase,
    val getCharacterById: GetCharacterByIdUseCase,
    val insertCharacter: InsertCharacterUseCase,
    val updateCharacter: UpdateCharacterUseCase,
    val deleteCharacter: DeleteCharacterUseCase,
)
