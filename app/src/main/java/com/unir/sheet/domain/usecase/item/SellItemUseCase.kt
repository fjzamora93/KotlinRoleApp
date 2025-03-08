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
            // Verificar que character.id no sea null
            val characterId = character.id ?: return Result.failure(Exception("El ID del personaje es nulo"))

            // Obtener la relación en la tabla intermedia
            val result = itemRepository.getCharacterItem(characterId, item.id)

            result.onSuccess { characterItem ->
                // Verificar si el personaje tiene el ítem y la cantidad suficiente
                if (characterItem!!.quantity <= 0) {
                    return Result.failure(Exception("No tienes el objeto que quieres vender"))
                }

                // Reducir la cantidad en la relación
                val newQuantity = characterItem.quantity - 1
                itemRepository.addItemToCharacter(characterId, item, newQuantity).onFailure { error ->
                    return Result.failure(error) // Manejar error al actualizar la cantidad
                }

                // Actualizar el oro del personaje
                val updatedCharacter = character.copy(gold = character.gold + item.goldValue)
                characterRepository.saveCharacter(updatedCharacter).onFailure { error ->
                    return Result.failure(error) // Manejar error al guardar el personaje
                }

                // Si la cantidad del ítem es 0, eliminar la relación
                if (newQuantity == 0) {
                    itemRepository.deleteItemById(characterId, item.id).onFailure { error ->
                        return Result.failure(error) // Manejar error al eliminar la relación
                    }
                }

                Result.success(Unit)
            }.onFailure { error ->
                return Result.failure(error) // Manejar error al obtener la relación
            }

            Result.failure(NoSuchElementException("No se encontró la relación, el objeto o el ítem que se quiere vender"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
