package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsBySessionUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(sessionId: Int): Result<List<Item>> {
        return repository.getItemsBySession(sessionId)
    }
}
