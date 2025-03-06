package com.unir.sheet.domain.usecase.item

import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.domain.repository.ItemRepository
import javax.inject.Inject

class DestroyItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend operator fun invoke(character: CharacterEntity, item: Item): Result<Unit> {
        // Verificar que character.id no sea null
        val characterId = character.id ?: return Result.failure(Exception("El ID del personaje es nulo"))

        // Llamar al repositorio para eliminar el ítem y propagar errores
        return repository.deleteItemById(characterId, item.id)
    }
}

