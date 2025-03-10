package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository
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
