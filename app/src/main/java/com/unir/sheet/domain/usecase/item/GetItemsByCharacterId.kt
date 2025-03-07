package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.CharacterItemDetail
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsByCharacterId @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(characterId: Int): Result<List<CharacterItemDetail>> {
        return try {
            val items = repository.getItemsByCharacterId(characterId)
            if (items.getOrNull().isNullOrEmpty()) {
                Result.failure(Exception("No se encontraron ítems para el personaje con ID $characterId"))
            } else {
                items
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
