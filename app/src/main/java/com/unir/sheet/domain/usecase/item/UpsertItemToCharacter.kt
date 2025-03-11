package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.data.model.Item
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository

/**Actualmente el parámetro QUANTITY se establece dentro de la base de datos, no aquí*/
class UpsertItemToCharacter(
    private val characterRepository: CharacterRepository,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(
        character: CharacterEntity,
        item: Item,
    ): Result<List<CharacterItemDetail>> {
        return try {
            var quantity: Int = itemRepository.getItemDetail(character.id, item.id).getOrThrow().quantity
            quantity += 1
            println("La cantidad que se va a modificar es... $quantity")

            if (character.gold < item.goldValue) {
                return Result.failure(Exception("Oro insuficiente"))
            }
            itemRepository.buyItem(character.id, item)
            itemRepository.upsertItemToCharacter(character.id, item, quantity)

            // Devolver el resultado actualizado
            return itemRepository.getItemsByCharacterId(character.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

