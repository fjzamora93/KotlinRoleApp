package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.RolCharacter
import com.unir.sheet.domain.repository.CharacterRepository
import javax.inject.Inject

// Agregar un ítem al personaje
class AddItemToCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: RolCharacter, item: Item) {
        repository.addItemToCharacter(character, item)
    }
}