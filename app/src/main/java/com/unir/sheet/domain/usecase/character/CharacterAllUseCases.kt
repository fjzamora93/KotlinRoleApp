package com.unir.sheet.domain.usecase.character

import com.unir.sheet.data.local.database.RolCharacterWithAllRelations
import com.unir.sheet.data.model.Item
import com.unir.sheet.data.model.RolCharacter
import com.unir.sheet.domain.repository.CharacterRepository
import javax.inject.Inject

// Obtener todos los personajes
class GetAllCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): List<RolCharacter> {
        return repository.getAllCharacters()
    }
}

// Obtener personajes por User ID
class GetCharacterByUserIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(userId: Int): List<RolCharacter> {
        return repository.getCharacterByUserId(userId)
    }
}

// Obtener personaje por ID
class GetCharacterByIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): RolCharacter? {
        return repository.getCharacterById(id)
    }
}

// Insertar personaje
class InsertCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: RolCharacter) {
        repository.insertCharacter(character)
    }
}

// Actualizar personaje
class UpdateCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: RolCharacter) {
        repository.updateCharacter(character)
    }
}

// Eliminar personaje
class DeleteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: RolCharacter) {
        repository.deleteCharacter(character)
    }
}

// Obtener personaje con todas sus relaciones
class GetCharacterWithRelationsUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(characterId: Int): RolCharacterWithAllRelations? {
        return repository.getCharacterWithRelations(characterId)
    }
}



