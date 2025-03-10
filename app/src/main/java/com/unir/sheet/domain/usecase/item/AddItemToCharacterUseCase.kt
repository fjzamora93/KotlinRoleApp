package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.data.model.Item
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository

/**Actualmente el parámetro QUANTITY se establece dentro de la base de datos, no aquí*/
class AddItemToCharacterUseCase(
    private val characterRepository: CharacterRepository,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        character: CharacterEntity,
        item: Item,
        quantity: Int = 1,
    ): Result<List<CharacterItemDetail>> {
        return try {
            if (character.gold < item.goldValue) {
                return Result.failure(Exception("Oro insuficiente"))
            }
            itemRepository.buyItem(character.id, item)
            return itemRepository.addItemToCharacter(character.id, item, quantity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

