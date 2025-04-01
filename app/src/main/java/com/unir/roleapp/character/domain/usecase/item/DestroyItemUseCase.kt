package com.roleapp.character.domain.usecase.item

import com.roleapp.character.data.model.local.Item
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterItemDetail
import com.roleapp.character.domain.repository.ItemRepository
import javax.inject.Inject


/**
 * A nivel local, los objetos pueden bajar la cantidad hasta 0, y no se borrarían de la base de datos.
 *
 * Esto es así para permitir que la base de adtos remota vaya borrando los objetos según lleguen a 0. El objetivo es que
 * cuando se sincronicen, la local tome los cambios de la remota ya con el borrado definitivo.
 *
 *
 * */
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

            return itemRepository.addItemToCharacter(character.id, item, quantity)


        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


