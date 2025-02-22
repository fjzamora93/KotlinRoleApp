package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class DestroyItemUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(character: CharacterEntity, item: Item): Boolean {
        val existingItemResult = itemRepository.getItemByCharacterIdAndItemId(character.id!!, item.id!!)
        return existingItemResult.fold(
            onSuccess = { existingItem ->
                // Disminuir en 1 la cantidad del item
                val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                if (updatedItem.quantity <= 0) {
                    itemRepository.deleteItem(updatedItem)
                } else {
                    itemRepository.insertOrUpdate(updatedItem)
                }
                true
            },
            onFailure = {
                false
            }
        )
    }
}

