package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsByCharacterId @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(characterId: Int): Result<List<Item>> {
        return repository.getItemsByCharacterId(characterId)
    }
}