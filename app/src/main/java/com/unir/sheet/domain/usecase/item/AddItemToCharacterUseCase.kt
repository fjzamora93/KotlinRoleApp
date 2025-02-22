package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class AddItemToCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(character: CharacterEntity, item: Item): Boolean {
        // Validar si el personaje tiene suficiente oro
        if (character.gold < item.goldValue) {
            return false // No tiene suficiente oro
        }

        // Obtener el ítem si ya lo tiene. Aumentar la cantidad si ya lo tiene, o crear uno nuevo si no lo tiene
        val existingItemResult = itemRepository.getItemByCharacterIdAndItemId(character.id!!, item.id!!)
        val updatedItem = existingItemResult.fold(
            onSuccess = { existingItem ->
                existingItem.copy(quantity = existingItem.quantity + item.quantity)
            },
            onFailure = {
                item.copy(characterId = character.id!!, quantity = item.quantity)
            }
        )

        // Actualizar el oro del personaje
        val updatedCharacter = character.copy(gold = character.gold - item.goldValue)

        // Guardar los cambios en la base de datos
        characterRepository.updateCharacter(updatedCharacter)
        itemRepository.insertOrUpdate(updatedItem)

        return true
    }
}


