package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class DestroyItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(character: CharacterEntity, item: Item): Result<List<CharacterItemDetail>> {
        return try {
            return repository.deleteItemFromCharacter(character.id, item.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

