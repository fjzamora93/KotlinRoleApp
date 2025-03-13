package com.unir.character.domain.usecase.item

import com.unir.character.data.model.local.CharacterEntity
import com.unir.character.data.model.local.CharacterItemDetail
import com.unir.character.data.model.local.Item
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.repository.ItemRepository

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
            return itemRepository.upsertItemToCharacter(character.id, item, quantity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

