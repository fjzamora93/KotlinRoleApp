package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class DestroyItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(item: Item): Result<Unit> {
        return repository.deleteItem(item) // Ya devuelve un Result<Unit>, así que solo lo propagamos.
    }
}


