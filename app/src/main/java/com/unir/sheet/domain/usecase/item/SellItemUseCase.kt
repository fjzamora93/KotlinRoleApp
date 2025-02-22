package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class SellItemUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(character: CharacterEntity, item: Item): Boolean {

        // Vender el ítem si ya lo tiene. Disminuir la cantidad si ya lo tiene y es superior a 0, aumentar el oro en el personaje.
        val existingItemResult = itemRepository.getItemByCharacterIdAndItemId(character.id!!, item.id!!)
        val updatedItem = existingItemResult.fold(
            onSuccess = { existingItem ->
                if (existingItem.quantity <= 0) return false
                existingItem.copy(quantity = existingItem.quantity - 1)
            },
            onFailure = { return false }
        )

        // Actualizar el oro del personaje
        val updatedCharacter = character.copy(gold = character.gold + item.goldValue)
        characterRepository.updateCharacter(updatedCharacter)

        if (updatedItem.quantity <= 0) {
            itemRepository.deleteItem(updatedItem)
        } else {
            itemRepository.insertOrUpdate(updatedItem)
        }

        return true
    }
}
