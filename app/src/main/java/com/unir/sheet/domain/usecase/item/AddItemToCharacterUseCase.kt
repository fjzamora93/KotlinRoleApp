package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.Item
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository

class AddItemToCharacterUseCase(
    private val characterRepository: CharacterRepository,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(character: CharacterEntity, item: Item): Result<Unit> {
        return try {
            if (character.gold < item.goldValue) {
                return Result.failure(Exception("Oro insuficiente"))
            }

            val updatedItem = item.copy(characterId = character.id)
            val updatedCharacter = character.copy(gold = character.gold - item.goldValue)

            // Actualizamos personaje e ítem asegurándonos de que ambas operaciones sean exitosas
            val characterUpdateResult = characterRepository.updateCharacter(updatedCharacter)
            val itemUpdateResult = itemRepository.insertOrUpdate(updatedItem)

            if (itemUpdateResult.isFailure) return itemUpdateResult

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

