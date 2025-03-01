package com.unir.sheet.data.repository

import com.unir.sheet.data.local.dao.CharacterDao
import com.unir.sheet.data.model.CharacterEntity
import com.unir.sheet.data.remote.service.ApiService
import com.unir.sheet.domain.repository.CharacterRepository
import javax.inject.Inject


/**
 * LAS CLASES DE ESTE REPOSITORIO DEBEN IMPLEMENTAR LA INTERFAZ DEL REPOSITORIO domain.repository.CharacterRepository
 *
 * Además, deben aplicar la lógica para decidir si la petición se va a realizar al repositorio remoto o al local.
 * */
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
    private val apiService: ApiService
) : CharacterRepository {


    override suspend fun getCharactersByUserId(userId: Int): Result<List<CharacterEntity>> {
        return try {
            val result = apiService.getCharactersByUserId(userId)
            if (result.isSuccessful) {
                val characters = result.body()
                if (characters != null) {
                    val charactersEntities = characters.map( { it.toCharacterEntity() } )
                    Result.success(charactersEntities)
                } else {
                    Result.failure(Exception("No se encontraron personajes para el usuario"))
                }
            } else {
                Result.failure(Exception("Error en la respuesta del servidor"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharacterById(id: Int): Result<CharacterEntity?> {
        return try {
            val result = apiService.getCharacterById(id)
            if (result.isSuccessful) {
                val character = result.body()
                if (character != null) {
                    Result.success(character.toCharacterEntity())
                } else {
                    Result.success(null) // Return null if no character is found
                }
            } else {
                Result.failure(Exception("Error en la respuesta: ${result.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun saveCharacter(character: CharacterEntity): Result<CharacterEntity> {
        return try {
            val result = apiService.saveCharacter(character.toApiRequest()) // Convert to CharacterResponse
            if (result.isSuccessful) {
                val savedCharacter = result.body()
                if (savedCharacter != null) {
                    Result.success(savedCharacter.toCharacterEntity()) // Convert back to CharacterEntity
                } else {
                    Result.failure(Exception("Error al guardar el personaje: Respuesta vacía")) // Return an error
                }
            } else {
                Result.failure(Exception("Error en la respuesta: ${result.code()}")) // Return an error
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun deleteCharacter(character: CharacterEntity): Result<Unit> {
        return try {
            val result = apiService.deleteCharacter(character.id!!)
            if (result.isSuccessful) {
                characterDao.deleteCharacter(character)
                Result.success(Unit) // Return success if both operations are successful
            } else {
                Result.failure(Exception("Error al eliminar el personaje en el servidor: ${result.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



}
