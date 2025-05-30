package com.roleapp.character.domain.usecase.item

import com.roleapp.character.data.model.local.CharacterItemDetail
import com.roleapp.character.data.model.local.Item
import com.roleapp.character.domain.repository.ItemRepository
import com.roleapp.character.domain.usecase.character.CharacterUseCases

/**Actualmente el parámetro QUANTITY se establece dentro de la base de datos, no aquí*/
class AddItemToCharacterUseCase(
    private val characterUseCase: CharacterUseCases,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        item: Item,
    ): Result<List<CharacterItemDetail>> {
        return try {
            val character = characterUseCase.getActiveCharacter().getOrNull()

            if (character?.id != null) {
                if (character.gold < item.goldValue) {
                    return Result.failure(Exception("Oro insuficiente"))
                }
                var quantity: Int =
                    itemRepository.getItemDetail(character.id, item.id).getOrThrow().quantity
                quantity += 1


                // Esto se actualiza dentro del dao del repositorio de item, no aquí
                //character.gold -= item.goldValue
                //characterUseCase.updateCharacter(character)
                itemRepository.buyItem(character.id, item)
                return itemRepository.addItemToCharacter(character.id, item, quantity)
            } else {
                Result.failure(Exception("Imposible añadir objeto a personaje"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

