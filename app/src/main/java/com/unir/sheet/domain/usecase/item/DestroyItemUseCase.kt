package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class DestroyItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        character: CharacterEntity,
        item: Item,
    ): Result<List<CharacterItemDetail>> {
        return try {
            var quantity: Int = itemRepository.getItemDetail(character.id, item.id).getOrThrow().quantity
            quantity -= 1
            println("La cantidad que se va a modificar es... $quantity")
            if (quantity <= 0) {
                itemRepository.deleteItemFromCharacter(character.id, item.id)
            } else {
                itemRepository.upsertItemToCharacter(character.id, item, quantity)
            }

            // Devolver el resultado actualizado
            return itemRepository.getItemsByCharacterId(character.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


