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
    suspend operator fun invoke(character: CharacterEntity, item: Item): Result<Unit> {
        return try {
            if (item.quantity <= 0) {
                return Result.failure(Exception("NO tienes el objeto que quieres vender"))
            }
            val updatedItem = item.copy(quantity = item.quantity - 1)

            // Actualizar el oro del personaje
            val updatedCharacter = character.copy(gold = character.gold + item.goldValue)
            characterRepository.saveCharacter(updatedCharacter)

            val itemUpdateResult = if (updatedItem.quantity <= 0) {
                itemRepository.deleteItem(updatedItem)
            } else {
                itemRepository.insertOrUpdate(updatedItem)
            }

            if (itemUpdateResult.isFailure) return itemUpdateResult
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
