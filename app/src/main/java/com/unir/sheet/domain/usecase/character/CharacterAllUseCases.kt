package com.unir.sheet.domain.usecase.character

import com.unir.sheet.data.local.dao.RolCharacterWithAllRelations
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.domain.repository.CharacterRepository
import com.unir.sheet.domain.usecase.skill.AddDefaultSkills
import com.unir.sheet.domain.usecase.skill.GetAllSkillsUseCase
import com.unir.sheet.domain.usecase.skill.SkillUseCases
import javax.inject.Inject

// Obtener todos los personajes
class GetAllCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): Result<List<CharacterEntity>> {
        return repository.getAllCharacters()
    }
}

// Obtener personajes por User ID
class GetCharacterByUserIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(userId: Int): Result<List<CharacterEntity>> {
        val result = repository.getCharacterByUserId(userId)
        return if (result.isSuccess) {
            Result.success(result.getOrNull() ?: emptyList())
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}


// Obtener personaje por ID
class GetCharacterByIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Result<CharacterEntity?> {
        val result = repository.getCharacterById(id)
        return if (result.isSuccess) {
            Result.success(result.getOrNull()) // Devolver el personaje o null si no se encuentra
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Character not found"))
        }
    }
}


// Insertar personaje
class InsertCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterEntity): Result<CharacterEntity?> {
        val result = repository.saveCharacter(character)
        return if (result.isSuccess) {
            Result.success(result.getOrNull()) // El personaje guardado
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Error al guardar el personaje"))
        }
    }
}


// Actualizar personaje
class UpdateCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository,
    private val addDefaultSkills: AddDefaultSkills
) {

    suspend operator fun invoke(character: CharacterEntity): Result<Unit> {
        val result = repository.saveCharacter(character)
        return if (result.isSuccess) {
            if (character.id == null) {
                result.onSuccess {
                        newCharacter -> addDefaultSkills(newCharacter)
                    println("INsertando habilidades")
                }
            }
            Result.success(Unit)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Error al actualizar el personaje"))
        }
    }
}


// Eliminar personaje
class DeleteCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(character: CharacterEntity): Result<Unit> {
        val result = repository.deleteCharacter(character)
        return if (result.isSuccess) {
            Result.success(Unit) // Indicar que la eliminación fue exitosa
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Error al eliminar el personaje"))
        }
    }
}







