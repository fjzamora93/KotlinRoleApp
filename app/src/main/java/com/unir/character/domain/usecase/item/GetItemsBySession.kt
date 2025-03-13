package com.unir.character.domain.usecase.item

import com.unir.character.data.model.local.Item
import com.unir.character.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsBySessionUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(sessionId: Int): Result<List<Item>> {
        return repository.getItemsBySession(sessionId)
    }
}
