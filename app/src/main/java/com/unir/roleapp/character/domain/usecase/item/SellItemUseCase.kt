package com.roleapp.character.domain.usecase.item

import com.roleapp.character.data.model.local.Item
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterItemDetail
import com.roleapp.character.domain.repository.CharacterRepository
import com.roleapp.character.domain.repository.ItemRepository
import javax.inject.Inject

class SellItemUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(character: CharacterEntity, item: Item): Result<List<CharacterItemDetail>>  {
        return try {
            itemRepository.sellItem(character.id, item)
            return itemRepository.deleteItemFromCharacter(character.id, item.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
